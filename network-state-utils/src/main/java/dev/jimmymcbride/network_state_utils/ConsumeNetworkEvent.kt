package dev.jimmymcbride.network_state_utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlin.reflect.KFunction1

@Composable
fun <T> ConsumeNetworkEvent(
    networkEvent: NetworkState<T>,
    consumeEvent: KFunction1<() -> Unit, Unit>,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit,
    onLoading: () -> Unit,
) {
    LaunchedEffect(key1 = networkEvent, block = {
        networkEvent.duringState(
            loading = { onLoading() }
        )
    })
    LaunchedEffect(key1 = networkEvent, block = {
        consumeEvent {
            networkEvent.duringState(
                success = { data -> onSuccess(data) },
                error = { message -> onError(message) },
                loading = { onLoading() }
            )
        }
    })
}