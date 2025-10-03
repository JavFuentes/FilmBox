package android.bootcamp.filmbox.view.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = phoneNumber)
        }
        verifyInputText()
    }

    fun onNameChanged(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
        verifyInputText()
    }

    fun onUserChanged(user: String) {
        _uiState.update {
            it.copy(user = user)
        }
        verifyInputText()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
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
    val isRegisterEnabled: Boolean = false
)