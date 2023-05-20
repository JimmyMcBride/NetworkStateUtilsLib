package dev.jimmymcbride.network_state_utils_lib.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.jimmymcbride.network_state_utils_lib.presentation.common.Layout
import dev.jimmymcbride.network_state_utils_lib.presentation.shared_view_models.CitiesViewModel
import dev.jimmymcbride.network_state_utils.DuringComposableState

@Composable
fun Home(
    navController: NavController,
    viewModel: CitiesViewModel,
) {
    val cities by viewModel.cities

    Layout(title = "Home", floatingActionButton = {
        IconButton(onClick = {
            navController.navigate("add_city")
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add city"
            )
        }
    }) {
        cities.DuringComposableState(
            success = { cities ->
                if (cities.isEmpty()) {
                    Text(text = "No cities in list currently")
                } else {
                    LazyColumn(
                        content = {
                            items(items = cities) { city ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(text = city.name)
                                    Text(text = city.population.toString())
                                }
                            }
                        }
                    )
                }
            },
            error = {
                Text(text = it)
            },
            loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(200.dp))
                }
            }
        )

    }

}