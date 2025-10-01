package android.bootcamp.filmbox.view.auth.login

import android.bootcamp.filmbox.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LoginScreen(){
    Scaffold { padding ->
        Column(
            Modifier.padding(padding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(3f))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo de la app"
            )

            Spacer(Modifier.weight(1f))

            var user by remember { mutableStateOf("") }
            TextField(value = user, onValueChange = { user = it})

            var password by remember { mutableStateOf("") }
            TextField(value = password, onValueChange = { password = it})

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            TextButton(onClick = {  }) {
                Text("¿Has olvidado la contraseña?")
            }

            Spacer(Modifier.weight(4f))

            OutlinedButton(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text("Registrarse")
            }

        }
    }
}