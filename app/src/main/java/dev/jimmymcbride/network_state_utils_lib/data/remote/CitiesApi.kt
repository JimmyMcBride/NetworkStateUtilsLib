package dev.jimmymcbride.network_state_utils_lib.data.remote

import dev.jimmymcbride.network_state_utils_lib.domain.entities.City
import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CitiesApi {
    @GET("/cities")
    suspend fun getCites(): Response<List<City>>

    @GET("/cities/{id}")
    suspend fun getCity(@Path("id") id: Int): Response<City>

    @POST("/cities")
    suspend fun addCity(@Body city: CityBody): Response<Int>

    @PUT("/cities/{id}")
    suspend fun updateCity(@Path("id") id: Int, @Body city: CityBody): Response<Boolean>

    @DELETE("/cities/{id}")
    suspend fun deleteCity(@Path("id") id: Int): Response<Boolean>
}