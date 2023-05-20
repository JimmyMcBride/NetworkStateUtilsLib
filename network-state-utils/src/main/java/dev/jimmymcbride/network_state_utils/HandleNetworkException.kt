package dev.jimmymcbride.network_state_utils

suspend fun <T> handleNetworkException(apiCall: suspend () -> NetworkState<T>) = try {
    apiCall()
} catch (e: Exception) {
    NetworkState.Error(message = e.message.toString())
}