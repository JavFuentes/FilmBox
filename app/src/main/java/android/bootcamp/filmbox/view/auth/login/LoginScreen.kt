package android.bootcamp.filmbox.view.auth.login

import android.bootcamp.filmbox.R
import android.bootcamp.filmbox.ui.theme.Amber400
import android.bootcamp.filmbox.ui.theme.AmberDesactivado
import android.bootcamp.filmbox.ui.theme.Indigo950
import android.bootcamp.filmbox.ui.theme.Slate200
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = Indigo950)
                .padding(horizontal = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(Modifier.weight(3f))
            Image(
                modifier = Modifier.size(225.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo de la app"
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Tu biblioteca de películas favoritas", color = Slate200
            )

            Spacer(Modifier.weight(1f))

            TextField(
                value = uiState.user,
                onValueChange = { loginViewModel.onUserChanged(it) },
                shape = MaterialTheme.shapes.medium,
                modifier =  Modifier.width(300.dp),
                label = { Text("Nombre de usuario", style = MaterialTheme.typography.labelLarge) },
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
                shape = MaterialTheme.shapes.medium,
                modifier =  Modifier.width(300.dp),
                label = { Text("Contraseña", style = MaterialTheme.typography.labelLarge) },
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

            Button(
                onClick = { },
                enabled = uiState.isLoginEnabled,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = AmberDesactivado,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Ingresar", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp))
            }

            TextButton(onClick = { }) {
                Text("¿Has olvidado la contraseña?", color = Slate200)
            }

            Spacer(Modifier.weight(4f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "¿No tienes cuenta?", modifier= Modifier.padding(8.dp), style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp) , color = Slate200)

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                ) {
                    Text("Registrar Aquí", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Amber400)
                }
            }
            Spacer(Modifier.weight(2f))

        }
    }
}