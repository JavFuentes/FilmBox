package android.bootcamp.filmbox.view.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUserChanged(user: String) {
        _uiState.update {
            _uiState.value.copy(user = user)
        }

        verifyInputText()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            _uiState.value.copy(password = password)
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

}


private fun isUserValid(user: String): Boolean = user.length >= 3
private fun isPasswordValid(password: String): Boolean = password.length >= 6

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false,
)