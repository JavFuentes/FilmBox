package android.bootcamp.filmbox.view.core.navigation

import android.bootcamp.filmbox.view.auth.login.LoginScreen
import android.bootcamp.filmbox.view.auth.register.RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Register){
        composable<Login> {
            LoginScreen()
        }

        composable<Register> {
            RegisterScreen()
        }
    }
}