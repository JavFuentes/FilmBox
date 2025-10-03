package android.bootcamp.filmbox.view.auth.register

import android.bootcamp.filmbox.R
import android.bootcamp.filmbox.ui.theme.Amber300
import android.bootcamp.filmbox.ui.theme.Amber400
import android.bootcamp.filmbox.ui.theme.AppShape
import android.bootcamp.filmbox.ui.theme.Indigo950
import android.bootcamp.filmbox.ui.theme.Slate200
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel()
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.app_logo_description)
            )

            Text(
                text = stringResource(R.string.register_title),
                textAlign = TextAlign.Center,
                color = Slate200,
                fontSize = 20.sp
            )

            Spacer(Modifier.weight(1f))

            FormRegisterParent(
                uiState = uiState,
                onPhoneNumberChanged = { registerViewModel.onPhoneNumberChanged(it) },
                onNameChanged = { registerViewModel.onNameChanged(it) },
                onUserChanged = { registerViewModel.onUserChanged(it) },
                onPasswordChanged = { registerViewModel.onPasswordChanged(it) }
            )

            ButtonsRegister(isRegisterEnabled = uiState.isRegisterEnabled)

            Spacer(Modifier.weight(1f))

            Text(
                text = stringResource(R.string.register_terms),
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Amber300
            )

            ButtonsFoot()
        }
    }
}

@Composable
fun ButtonsRegister(isRegisterEnabled: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            enabled = isRegisterEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = Amber300,
                disabledContentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(stringResource(R.string.register_button))
        }
    }
}

@Composable
fun ButtonsFoot() {
    Row(
        modifier = Modifier
            .width(300.dp)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = {}) {
            Text(
                stringResource(R.string.register_has_account),
                color = Slate200
            )
        }

        OutlinedButton(
            onClick = { },
            shape = AppShape.large
        ) {
            Text(
                stringResource(R.string.register_login_button),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Amber400
            )
        }
    }
}

@Composable
fun FormRegisterParent(
    uiState: RegisterUiState,
    onPhoneNumberChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onUserChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyPhoneNumberField(
            phoneNumber = uiState.phoneNumber,
            onValueChange = onPhoneNumberChanged
        )
        MyNameField(
            name = uiState.name,
            onValueChange = onNameChanged
        )
        MyUserField(
            user = uiState.user,
            onValueChange = onUserChanged
        )
        MyPasswordField(
            password = uiState.password,
            onValueChange = onPasswordChanged
        )
    }
}

@Composable
fun MyPhoneNumberField(phoneNumber: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = phoneNumber,
        shape = AppShape.medium,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                stringResource(R.string.register_phone_label),
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
        )
    )
}

@Composable
fun MyNameField(name: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = name,
        shape = AppShape.medium,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                stringResource(R.string.register_name_label),
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
        )
    )
}

@Composable
fun MyUserField(user: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = user,
        shape = AppShape.medium,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                stringResource(R.string.register_username_label),
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
        )
    )
}

@Composable
fun MyPasswordField(password: String, onValueChange: (String) -> Unit) {
    var passwordHidden by remember { mutableStateOf(true) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = password,
        shape = AppShape.medium,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                stringResource(R.string.register_password_label),
                style = MaterialTheme.typography.labelLarge
            )
        },
        singleLine = true,
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
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Indigo950,
            focusedTextColor = Indigo950,
            unfocusedContainerColor = Slate200,
            focusedContainerColor = Slate200,
            cursorColor = Indigo950,
            focusedIndicatorColor = Slate200,
            unfocusedIndicatorColor = Slate200
        )
    )
}