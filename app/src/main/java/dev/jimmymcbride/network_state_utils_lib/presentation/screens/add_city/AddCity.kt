package dev.jimmymcbride.network_state_utils_lib.presentation.screens.add_city

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.jimmymcbride.network_state_utils_lib.domain.entities.CityBody
import dev.jimmymcbride.network_state_utils_lib.presentation.common.Layout
import dev.jimmymcbride.network_state_utils_lib.presentation.shared_view_models.CitiesViewModel
import dev.jimmymcbride.network_state_utils.ConsumeNetworkEvent

@Composable
fun AddCity(
    navController: NavController,
    citiesViewModel: CitiesViewModel,
) {
    val addCityEvent by citiesViewModel.addCityEvent
    var isLoading by remember {
        mutableStateOf(false)
    }

    Layout(title = "Add City", navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back button")
        }
    }) {
        ConsumeNetworkEvent(
            networkEvent = addCityEvent,
            consumeEvent = citiesViewModel::consumeAddCityEvent,
            onSuccess = {
                isLoading = false
                navController.popBackStack()
            },
            onError = {
                isLoading = false
            },
            onLoading = { isLoading = true }
        )
        var name by remember {
            mutableStateOf("")
        }
        var population by remember {
            mutableStateOf("")
        }
        AnimatedVisibility(visible = isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter city name") },
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            value = population,
            onValueChange = { population = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Enter population") },
        )
        Button(onClick = {
            citiesViewModel.addCity(
                CityBody(
                    name,
                    population = population.toInt()
                )
            )
        }) {
            Text(text = "Add city")
        }
    }
}
