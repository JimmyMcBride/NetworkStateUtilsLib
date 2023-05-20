package dev.jimmymcbride.network_state_utils

import androidx.compose.runtime.Composable

@Composable
fun <T> NetworkState<T>.DuringComposableState(
    success: @Composable (T) -> Unit = {},
    error: @Composable (String) -> Unit = {},
    idle: @Composable () -> Unit = {},
    loading: @Composable () -> Unit = {},
) {
    when (this) {
        is NetworkState.Success -> success(this.data)
        is NetworkState.Error -> error(this.message)
        is NetworkState.Idle -> idle()
        is NetworkState.Loading -> loading()
    }
}