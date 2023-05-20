package dev.jimmymcbride.network_state_utils_lib.domain.use_cases

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.jimmymcbride.network_state_utils_lib.domain.entities.City
import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository
import dev.jimmymcbride.network_state_utils.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GetCitiesUseCase(
    private val repository: Repository,
) {
    private var cities = mutableStateOf<NetworkState<List<City>>>(NetworkState.Idle)
    operator fun invoke(coroutineScope: CoroutineScope): MutableState<NetworkState<List<City>>> {
        cities.value = NetworkState.Loading
        coroutineScope.launch {
            delay(2000L)
            cities.value = repository.getCities()
        }
        return cities
    }
}