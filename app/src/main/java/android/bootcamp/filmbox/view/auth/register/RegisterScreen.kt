package android.bootcamp.filmbox.view.auth.register

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.bootcamp.filmbox.R
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun RegisterScreen(

){
    Scaffold { padding ->
        Column(Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.weight(1f))
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo de la App"
            )
            Text(text = "Regístrate para acceder\na tu caja de películas",
                textAlign = TextAlign.Center,
                fontSize = 20.sp)


            Spacer(Modifier.weight(1f))

            FormRegisterParent()

            ButtonsRegister()

            Spacer(Modifier.weight(1f))
            Text(text = "Al registrate, aceptas nuestras\nCondiciones y Política de privacidad",
                Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light)
            HorizontalDivider()

            ButtonsFoot()

        }

    }

}


@Composable
fun ButtonsRegister(){

    Column (Modifier.fillMaxWidth()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text("Registrar")
        }

    }
}

@Composable
fun ButtonsFoot(){

    Row (Modifier.width(300.dp)
        .padding(20.dp),
        horizontalArrangement = Arrangement.Center) {
        TextButton(onClick = {}) {
            Text("¿Tienes una cuenta?")
        }
        TextButton(onClick = {}) {
            Text("Entrar", fontWeight = FontWeight.Bold)
        }

    }
}
@Composable
fun FormRegisterParent(){
    var phoneNumberOrEmail by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        MyPhoneNumberOrEmailField(phoneNumberOrEmail = phoneNumberOrEmail) { phoneNumberOrEmail = it }
        MyNameField(name = name) { name = it }
        MyUserField(user = user) { user = it }
        MyPasswordField(password = password) { password = it }
    }
}

@Composable
fun MyPhoneNumberOrEmailField(phoneNumberOrEmail: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        value = phoneNumberOrEmail,
        onValueChange = { onValueChange(it) },
        label = { Text("Número de móvil o correo electrónico")}
    )
}
@Composable
fun MyNameField(name: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        value = name,
        onValueChange = { onValueChange(it) },
        label = { Text("Nombre completo")}
    )
}
@Composable
fun MyUserField(user: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        value = user,
        onValueChange = { onValueChange(it) },
        label = { Text("Nombre de usuario")}
    )
}

@Composable
fun MyPasswordField(password: String, onValueChange: (String) -> Unit){
    var passwordHidden by remember {mutableStateOf(true)}
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        value = password,
        onValueChange = { onValueChange(it) },
        label = { Text("Contraseña")},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if(passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {

            val visibilityIcon =
                if (passwordHidden) R.drawable.visibility else R.drawable.visibility_off

            val description = if (passwordHidden) "Mostrar contraseña" else "Ocultar contraseña"

            Icon(
                painter = painterResource(id = visibilityIcon),
                contentDescription = description,
                modifier = Modifier.clickable {
                    passwordHidden = !passwordHidden
                }
            )

        }


    )
}