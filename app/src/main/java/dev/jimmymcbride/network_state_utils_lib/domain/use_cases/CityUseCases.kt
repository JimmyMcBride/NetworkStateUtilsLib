package dev.jimmymcbride.network_state_utils_lib.domain.use_cases

data class CityUseCases(
    val getCitiesUseCase: GetCitiesUseCase,
    val getCityUseCase: GetCityUseCase,
    val addCityUseCase: AddCityUseCase,
    val updateCityUseCase: UpdateCityUseCase,
    val deleteCityUseCase: DeleteCityUseCase,
)
