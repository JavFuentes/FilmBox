package android.bootcamp.filmbox.view.core.navigation

import android.bootcamp.filmbox.view.auth.login.LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login){
        composable<Login> {
            LoginScreen()
        }

        composable<Register> {

        }
    }

}