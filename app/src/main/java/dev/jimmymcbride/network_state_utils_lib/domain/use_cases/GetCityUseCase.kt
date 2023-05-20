package dev.jimmymcbride.network_state_utils_lib.domain.use_cases

import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository

class GetCityUseCase(
    private val repository: Repository,
) {
    operator fun invoke() {}
}