package cz.cvut.fit.poliskyr.trainmeapp.navigation

import androidx.compose.foundation.background
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.cvut.fit.poliskyr.trainmeapp.components.Drawer
import cz.cvut.fit.poliskyr.trainmeapp.presentation.AuthViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.MainViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainersViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainingsViewModel
import cz.cvut.fit.poliskyr.trainmeapp.screen.*
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Background
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Warning
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    trainersViewModel: TrainersViewModel,
    trainingsViewModel: TrainingsViewModel,
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    Surface(color = Background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            modifier = Modifier.background(Background),
            drawerContent = {
                Drawer(
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
//                startDestination = Screen.MainScreen.route,
                startDestination = Screen.AuthScreen.route
            ) {
                composable(route = Screen.AuthScreen.route) {
                    AuthScreen(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
                composable(route = Screen.MainScreen.route) {
                    MainScreen(
                        navController = navController,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(route = Screen.TrainersScreen.route) {
                    TrainersScreen(
                        navController = navController,
                        trainersViewModel = trainersViewModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(route = Screen.TrainingsScreen.route) {
                    TrainingsScreen(
                        navController = navController,
                        trainingsViewModel = trainingsViewModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(route = Screen.ContactsScreen.route) {
                    ContactsScreen(
                        navController = navController,
                    )
                }
            }
        }
    }
}