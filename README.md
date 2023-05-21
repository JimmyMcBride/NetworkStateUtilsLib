# Network State Utils

Network state utils package is just a small package that offers some tools to make handling network 
states and events a breeze!

## Installation

To install simply add the package to your dependencies add jitpack to root build gradle:

```groovy
allprojects {
	repositories {
		// ...
		maven { url 'https://jitpack.io' }
	}
}
```

Then add the dependency to your module gradle.

```groovy
dependencies {
    implementation 'com.github.JimmyMcBride:NetworkStateUtilsLib:1.0.1'
}
```

## NetworkState

`NetworkState` is a sealed class to handle... you guessed it, our network state. 

```kotlin
sealed class NetworkState<out T> {
    object Idle : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val message: String) : NetworkState<Nothing>()
}
```

## duringState

`duringState` is an extension function for `NetworkState` that helps us handle what happens during
any given state the network request is in.

```kotlin
networkEvent.duringState(
    success = { data -> onSuccess(data) },
    error = { message -> onError(message) },
    loading = { onLoading() },
    idle = { onIdle() }
)
```

## DuringComposableState

Very similar to `duringState` but allows you to add composable functions inside each state block.

```kotlin
networkEvent.DuringComposableState(
    success = { data ->
        Text(data.name)
    },
    error = { message ->
        Text("N/A")
        showSnackbar(message)
    },
    loading = { CircularProgressIndicator() },
    idle = {}
)
```

## ConsumeNetworkEvent

Sometimes you'll want to reset the `NetworkState` back to Idle after a specific even has occurred. Be
sure to include a clean up function resetting `NetworkState` back to idle in the view model for 
`ConsumeNetworkEvent` to use.

```kotlin
@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val cityUseCases: CityUseCases
) : ViewModel() {
    private var _cities = cityUseCases.getCitiesUseCase(viewModelScope)
    val cities: State<NetworkState<List<City>>> = _cities

    private val _addCityEvent = mutableStateOf<NetworkState<Int>>(NetworkState.Idle)
    val addCityEvent: State<NetworkState<Int>> = _addCityEvent

    fun addCity(city: CityBody) {
        _addCityEvent.value = NetworkState.Loading
        viewModelScope.launch {
            delay(2000L)
            _addCityEvent.value = cityUseCases.addCityUseCase(city).duringState(
                success = {
                    fetchCities()
                }
            )
        }
    }

    fun consumeAddCityEvent(triggeredBlock: () -> Unit) {
        triggeredBlock()
        _addCityEvent.value = NetworkState.Idle
    }
}
```

```kotlin
ConsumeNetworkEvent(
    networkEvent = addCityEvent,
    consumeEvent = citiesViewModel::consumeAddCityEvent,
    onSuccess = {
        isLoading = false
        navController.popBackStack()
    },
    onError = {
        isLoading = false
    },
    onLoading = { isLoading = true }
)
```

## handleNetworkException and handleResponse

`handleNetworkException` and `handleResponse` are just some small utils to help up clean up our 
repository.

```kotlin
fun <T> Response<T>.handleResponse(errorMessage: String = "Something went wrong.") =
    if (this.isSuccessful && this.body() != null)
        NetworkState.Success(data = this.body()!!)
    else
        NetworkState.Error(message = errorMessage)

suspend fun <T> handleNetworkException(apiCall: suspend () -> NetworkState<T>) = try {
    apiCall()
} catch (e: Exception) {
    NetworkState.Error(message = e.message.toString())
}
```

```kotlin
class RepositoryImpl(
    private val citiesApi: CitiesApi,
) : Repository {
    override suspend fun getCities(): NetworkState<List<City>> =
        handleNetworkException { citiesApi.getCites().handleResponse() }
}
```

If you have any questions feel free to email me @ hello@jimmymcbride.dev or leave an issue on this
repository if you notice and bugs or have any requests.
