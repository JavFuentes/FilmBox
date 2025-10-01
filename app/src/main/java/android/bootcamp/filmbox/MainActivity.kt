package android.bootcamp.filmbox

import android.bootcamp.filmbox.ui.theme.FilmBoxTheme
import android.bootcamp.filmbox.view.core.navigation.AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmBoxTheme {
                AppNavigation()
            }
        }
    }
}
