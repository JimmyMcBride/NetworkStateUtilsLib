package dev.jimmymcbride.network_state_utils

import retrofit2.Response

fun <T> Response<T>.handleResponse(errorMessage: String = "Something went wrong.") =
    if (this.isSuccessful && this.body() != null)
        NetworkState.Success(data = this.body()!!)
    else
        NetworkState.Error(message = errorMessage)

