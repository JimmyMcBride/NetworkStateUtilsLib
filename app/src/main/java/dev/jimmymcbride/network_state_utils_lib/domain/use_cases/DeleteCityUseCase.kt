package dev.jimmymcbride.network_state_utils_lib.domain.use_cases

import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository
import dev.jimmymcbride.network_state_utils.NetworkState

class DeleteCityUseCase(
    private val repository: Repository,
) {
    suspend operator fun invoke(id: Int): NetworkState<Boolean> {
        return repository.deleteCity(id)
    }
}