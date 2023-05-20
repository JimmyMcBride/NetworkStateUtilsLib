package dev.jimmymcbride.network_state_utils_lib.domain.repository

import dev.jimmymcbride.network_state_utils_lib.domain.entities.City
import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import dev.jimmymcbride.network_state_utils.NetworkState

interface Repository {
    suspend fun getCities(): NetworkState<List<City>>
    suspend fun getCity(id: Int): NetworkState<City>
    suspend fun addCity(city: CityBody): NetworkState<Int>
    suspend fun updateCity(id: Int, city: CityBody): NetworkState<Boolean>
    suspend fun deleteCity(id: Int): NetworkState<Boolean>
}