package dev.jimmymcbride.network_state_utils_lib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jimmymcbride.network_state_utils_lib.presentation.screens.add_city.AddCity
import dev.jimmymcbride.network_state_utils_lib.presentation.screens.home.Home
import dev.jimmymcbride.network_state_utils_lib.presentation.shared_view_models.CitiesViewModel
import dev.jimmymcbride.network_state_utils_lib.presentation.ui.theme.RickAndMortyTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val citiesViewModel by viewModels<CitiesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable(route = "home") {
                        Home(navController, citiesViewModel)
                    }
                    composable(route = "add_city") {
                        AddCity(navController, citiesViewModel)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTestTheme {
        Greeting("Android")
    }
}