package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val userProgress by mainViewModel.userProgress.collectAsState()

            // Support both manual toggle and system defaults for kids' dark mode
            MyApplicationTheme(darkTheme = userProgress.isDarkMode) {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                progress = userProgress,
                                onNavigate = { route -> navController.navigate(route) },
                                onToggleLanguage = { mainViewModel.toggleLanguage() },
                                onToggleDarkMode = { mainViewModel.toggleDarkMode() },
                                viewModel = mainViewModel
                            )
                        }
                        composable("abc") {
                            AbcScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("numbers") {
                            NumbersScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("colors") {
                            ColorsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("animals") {
                            AnimalsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("fruits") {
                            FruitsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("vehicles") {
                            VehiclesScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("shapes") {
                            ShapesScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("memory") {
                            MemoryScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("tracing") {
                            TracingScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("balloon_pop") {
                            BalloonPopScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("habits") {
                            HabitsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("opposites") {
                            OppositesScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("planets") {
                            SolarSystemScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("instruments") {
                            InstrumentsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("birds") {
                            BirdsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("vegetables") {
                            VegetablesScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("body_parts") {
                            BodyPartsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("quiz") {
                            QuizScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("drawing") {
                            DrawingScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("parent_rewards") {
                            ParentRewardsScreen(
                                progress = userProgress,
                                viewModel = mainViewModel,
                                lang = userProgress.lang,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
