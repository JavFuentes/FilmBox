package android.bootcamp.filmbox.view.core.navigation

import android.bootcamp.filmbox.view.auth.login.LoginScreen
import android.bootcamp.filmbox.view.auth.register.RegisterScreen
import android.bootcamp.filmbox.view.home.HomeScreen
import android.bootcamp.filmbox.view.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {

        // Pantalla inicial que verifica si hay sesión activa
        composable<Splash> {
            SplashScreen(
                // Callback cuando no hay usuario autenticado
                navigateToLogin = {
                    navController.navigate(Login, navOptions {
                        // Elimina Splash del backstack para que no se pueda volver con botón atrás
                        popUpTo(Splash) { inclusive = true }
                    })
                },
                // Callback cuando hay usuario autenticado
                navigateToHome = {
                    navController.navigate(Home, navOptions {
                        // Elimina Splash del backstack para que no se pueda volver con botón atrás
                        popUpTo(Splash) { inclusive = true }
                    })
                }
            )
        }

        composable<Login> {
            LoginScreen(
                navigateToRegister = { navController.navigate(Register) },
                navigateToHome = {
                    navController.navigate(Home, navOptions {
                        // Elimina Login del backstack para que no se pueda volver con botón atrás
                        popUpTo(Login) { inclusive = true }
                    })
                }
            )
        }

        composable<Register> {
            RegisterScreen(navigateBack = { navController.popBackStack() })
        }

        composable<Home> {
            HomeScreen()
        }
    }
}