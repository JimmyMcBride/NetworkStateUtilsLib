package dev.jimmymcbride.network_state_utils_lib.presentation.shared_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jimmymcbride.network_state_utils_lib.domain.entities.City
import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import dev.jimmymcbride.network_state_utils_lib.domain.use_cases.CityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jimmymcbride.network_state_utils.NetworkState
import dev.jimmymcbride.network_state_utils.duringState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun updateCity(id: Int, city: CityBody) {
        viewModelScope.launch {
            cityUseCases.updateCityUseCase(id, city).duringState(
                success = {
                    fetchCities()
                }
            )
        }
    }

    fun deleteCity(id: Int) {
        viewModelScope.launch {
            cityUseCases.deleteCityUseCase(id).duringState(
                success = {
                    fetchCities()
                }
            )
        }
    }

    private fun fetchCities() {
        _cities.value = NetworkState.Loading
        viewModelScope.launch {
            _cities = cityUseCases.getCitiesUseCase(this)
        }
    }
}
