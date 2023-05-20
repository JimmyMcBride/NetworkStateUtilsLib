package dev.jimmymcbride.network_state_utils_lib.domain.use_cases

import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository
import dev.jimmymcbride.network_state_utils.NetworkState

class AddCityUseCase(
    private val repository: Repository,
) {
    suspend operator fun invoke(city: CityBody): NetworkState<Int> {
        return repository.addCity(city)
    }
}