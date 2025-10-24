package android.bootcamp.filmbox.view.auth.register

import android.bootcamp.filmbox.data.repository.AuthRepository
import android.bootcamp.filmbox.view.auth.login.LoginUiState
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = phoneNumber, errorMessage = null)
        }
        verifyInputText()
    }

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(name = name, errorMessage = null)
        }
        verifyInputText()
    }

    fun onUserChanged(user: String) {
        _uiState.update {
            it.copy(user = user, errorMessage = null)
        }
        verifyInputText()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password, errorMessage = null)
        }
        verifyInputText()
    }

    private fun verifyInputText() {
        val enabledRegister = isPhoneNumberValid(_uiState.value.phoneNumber) &&
                isNameValid(_uiState.value.name) &&
                isUserValid(_uiState.value.user) &&
                isPasswordValid(_uiState.value.password)

        _uiState.update {
            it.copy(isRegisterEnabled = enabledRegister)
        }
    }

    fun clearState(){
        _uiState.update {
            RegisterUiState()
        }
    }

    fun register(onSuccess: () -> Unit) {
        // Lanza una corrutina en el scope del ViewModel para operación asíncrona
        viewModelScope.launch {

            // Actualiza el estado UI para mostrar loading y limpiar errores previos
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Llama al repositorio para registrar usuario con los datos del formulario
            val result = authRepository.register(
                username = _uiState.value.user,
                password = _uiState.value.password,
                name = _uiState.value.name,
                phoneNumber = _uiState.value.phoneNumber
            )

            // Maneja el resultado usando fold (similar a un switch para Result)
            result.fold(
                onSuccess = {
                    // Actualiza estado a éxito y oculta loading
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registerSuccess = true
                        )
                    }
                    // Ejecuta callback de éxito (navegar a otra pantalla, etc.)
                    onSuccess()
                },
                onFailure = { exception ->
                    // Actualiza estado con mensaje de error y oculta loading
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
            exception.message?.contains("already in use") == true ->
                "Este usuario ya está registrado"
            exception.message?.contains("network") == true ->
                "Error de conexión. Verifica tu internet"
            exception.message?.contains("weak-password") == true ->
                "La contraseña es muy débil"
            else -> "Error al registrar: ${exception.message ?: "Desconocido"}"
        }
    }
}

private fun isPhoneNumberValid(phoneNumber: String): Boolean {
    return phoneNumber.isNotBlank() &&
            Patterns.PHONE.matcher(phoneNumber).matches()
}

private fun isNameValid(name: String): Boolean = name.length >= 3

private fun isUserValid(user: String): Boolean = user.length >= 3

private fun isPasswordValid(password: String): Boolean = password.length >= 6

data class RegisterUiState(
    val phoneNumber: String = "",
    val name: String = "",
    val user: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isRegisterEnabled: Boolean = false,
    val registerSuccess: Boolean = false,
    val errorMessage:String? = null
)