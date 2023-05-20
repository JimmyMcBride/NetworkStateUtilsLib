package dev.jimmymcbride.network_state_utils

fun <T> NetworkState<T>.duringState(
    success: (T) -> Unit = {},
    error: (String) -> Unit = {},
    idle: () -> Unit = {},
    loading: () -> Unit = {},
): NetworkState<T> {
    when (this) {
        is NetworkState.Success -> success(this.data)
        is NetworkState.Error -> error(this.message)
        is NetworkState.Idle -> idle()
        is NetworkState.Loading -> loading()
    }
    return this
}