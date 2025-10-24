package android.bootcamp.filmbox.view.splash

import android.bootcamp.filmbox.R
import android.bootcamp.filmbox.ui.theme.Amber400
import android.bootcamp.filmbox.ui.theme.Indigo950
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = viewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by splashViewModel.uiState.collectAsStateWithLifecycle()

    // Observar cambios en el estado de autenticación
    LaunchedEffect(uiState.authState) {
        when (uiState.authState) {
            is AuthState.Authenticated -> {
                // Usuario autenticado, ir a Home
                navigateToHome()
            }
            is AuthState.Unauthenticated -> {
                // Sin sesión, ir a Login
                navigateToLogin()
            }
            is AuthState.Loading -> {
                // Verificando, no hacer nada
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Indigo950),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(225.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.app_logo_description)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(
                color = Amber400,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
