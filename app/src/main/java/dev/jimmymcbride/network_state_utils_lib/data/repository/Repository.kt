package dev.jimmymcbride.network_state_utils_lib.data.repository

import dev.jimmymcbride.network_state_utils_lib.data.remote.CitiesApi
import dev.jimmymcbride.network_state_utils_lib.domain.entities.City
import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import dev.jimmymcbride.network_state_utils_lib.domain.repository.Repository
import dev.jimmymcbride.network_state_utils.NetworkState
import dev.jimmymcbride.network_state_utils.handleNetworkException
import dev.jimmymcbride.network_state_utils.handleResponse

class RepositoryImpl(
    private val citiesApi: CitiesApi,
) : Repository {
    override suspend fun getCities(): NetworkState<List<City>> =
        handleNetworkException { citiesApi.getCites().handleResponse() }

    override suspend fun getCity(id: Int): NetworkState<City> =
        handleNetworkException { citiesApi.getCity(id).handleResponse() }

    override suspend fun addCity(city: CityBody): NetworkState<Int> =
        handleNetworkException { citiesApi.addCity(city).handleResponse() }

    override suspend fun updateCity(id: Int, city: CityBody): NetworkState<Boolean> =
        handleNetworkException { citiesApi.updateCity(id, city).handleResponse() }

    override suspend fun deleteCity(id: Int): NetworkState<Boolean> =
        handleNetworkException { citiesApi.deleteCity(id).handleResponse() }
}
