package android.bootcamp.filmbox.view.auth.login

import android.bootcamp.filmbox.R
import android.bootcamp.filmbox.ui.theme.Amber300
import android.bootcamp.filmbox.ui.theme.Amber400
import android.bootcamp.filmbox.ui.theme.AppShape
import android.bootcamp.filmbox.ui.theme.Indigo950
import android.bootcamp.filmbox.ui.theme.Slate200
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    var passwordHidden by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        loginViewModel.clearState()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = Indigo950)
                .padding(horizontal = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(3f))

            Image(
                modifier = Modifier.size(225.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.app_logo_description)
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = stringResource(R.string.login_tagline),
                textAlign = TextAlign.Center,
                color = Slate200,


            )

            Spacer(Modifier.weight(1f))

            TextField(
                value = uiState.user,
                onValueChange = { loginViewModel.onUserChanged(it) },
                shape = AppShape.medium,
                modifier = Modifier.width(300.dp),
                label = {
                    Text(
                        stringResource(R.string.login_username_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Indigo950,
                    focusedTextColor = Indigo950,
                    unfocusedContainerColor = Slate200,
                    focusedContainerColor = Slate200,
                    cursorColor = Indigo950,
                    focusedIndicatorColor = Slate200,
                    unfocusedIndicatorColor = Slate200
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Indigo950
                )
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                shape = AppShape.medium,
                modifier = Modifier.width(300.dp),
                label = {
                    Text(
                        stringResource(R.string.login_password_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Indigo950,
                    focusedTextColor = Indigo950,
                    unfocusedContainerColor = Slate200,
                    focusedContainerColor = Slate200,
                    cursorColor = Indigo950,
                    focusedIndicatorColor = Slate200,
                    unfocusedIndicatorColor = Slate200
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Indigo950
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    val visibilityIcon = if (passwordHidden) {
                        R.drawable.visibility
                    } else {
                        R.drawable.visibility_off
                    }

                    val description = if (passwordHidden) {
                        stringResource(R.string.password_show)
                    } else {
                        stringResource(R.string.password_hide)
                    }

                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = description,
                        modifier = Modifier.clickable {
                            passwordHidden = !passwordHidden
                        }
                    )
                }
            )

            Spacer(Modifier.height(16.dp))

            // Mostrar mensaje de error si existe
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = { loginViewModel.login { navigateToHome() } },
                enabled = uiState.isLoginEnabled && !uiState.isLoading,
                shape = AppShape.large,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = Amber300,
                    disabledContentColor = Color.White
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        stringResource(R.string.login_button),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                    )
                }
            }

            TextButton(onClick = { }) {
                Text(
                    stringResource(R.string.login_forgot_password),
                    color = Slate200
                )
            }

            Spacer(Modifier.weight(4f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.login_no_account),
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = Slate200
                )

                OutlinedButton(
                    onClick = { navigateToRegister() },
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        stringResource(R.string.login_register_here),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Amber400
                    )
                }
            }

            Spacer(Modifier.weight(2f))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){

}