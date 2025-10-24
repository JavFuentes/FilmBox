package android.bootcamp.filmbox.view.auth.login

import android.bootcamp.filmbox.data.repository.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUserChanged(user: String) {
        _uiState.update {
            _uiState.value.copy(user = user, errorMessage = null)
        }

        verifyInputText()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            _uiState.value.copy(password = password, errorMessage = null)
        }

        verifyInputText()
    }

    fun verifyInputText() {
        val enabledLogin =
            isUserValid(_uiState.value.user) && isPasswordValid(_uiState.value.password)
        _uiState.update {
            it.copy(isLoginEnabled = enabledLogin)
        }
    }

    fun clearState(){
        _uiState.update {
            LoginUiState()
        }
    }

    fun login(onSuccess: () -> Unit) {
        // Lanza corrutina en el scope del ViewModel para operación asíncrona
        viewModelScope.launch {
            // Activa estado de carga y limpia mensajes de error previos
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Llama al repositorio para autenticar con Firebase usando credenciales del estado
            val result = authRepository.login(
                username = _uiState.value.user,
                password = _uiState.value.password
            )

            // Maneja el resultado del login (éxito o fallo)
            result.fold(
                onSuccess = {
                    // Actualiza el estado indicando login exitoso y desactiva carga
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true
                        )
                    }
                    // Ejecuta callback de navegación a la pantalla Home
                    onSuccess()
                },
                onFailure = { exception ->
                    // Actualiza el estado con mensaje de error traducido y desactiva carga
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = getErrorMessage(exception)
                        )
                    }
                }
            )
        }
    }

    private fun getErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("no user record") == true ||
                    exception.message?.contains("INVALID_LOGIN_CREDENTIALS") == true ||
                    exception.message?.contains("malformed") == true ||
                    exception.message?.contains("invalid-credential") == true ->
                "Usuario o contraseña incorrectos"
            exception.message?.contains("network") == true ->
                "Error de conexión. Verifica tu internet"
            exception.message?.contains("too-many-requests") == true ->
                "Demasiados intentos. Intenta más tarde"
            else -> "Error al iniciar sesión: ${exception.message ?: "Desconocido"}"
        }
    }
}

private fun isUserValid(user: String): Boolean = user.length >= 3
private fun isPasswordValid(password: String): Boolean = password.length >= 6

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val loginSuccess: Boolean = false,
    val errorMessage:String? = null
)