package android.bootcamp.filmbox.view.splash

import android.bootcamp.filmbox.data.repository.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentFirebaseUser()

            if (currentUser != null) {
                // Usuario autenticado, navegar a Home
                _uiState.update {
                    it.copy(authState = AuthState.Authenticated)
                }
            } else {
                // Sin sesión activa, navegar a Login
                _uiState.update {
                    it.copy(authState = AuthState.Unauthenticated)
                }
            }
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}

data class SplashUiState(
    val authState: AuthState = AuthState.Loading,
)