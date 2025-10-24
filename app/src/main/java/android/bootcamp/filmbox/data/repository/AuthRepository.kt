package android.bootcamp.filmbox.data.repository

import android.bootcamp.filmbox.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val usersCollection = firestore.collection("users")

    /**
     * Registra un nuevo usuario en Firebase Auth y guarda sus datos en Firestore
     * @param username Username del usuario (se convertirá a email format)
     * @param password Contraseña del usuario
     * @param name Nombre completo del usuario
     * @param phoneNumber Número de teléfono del usuario
     * @return Result con el User creado o un error
     */
    suspend fun register(
        username: String,
        password: String,
        name: String,
        phoneNumber: String
    ): Result<User> {
        return try {
            // Convertir username a formato email para Firebase Auth
            // Sanitizar el username: remover caracteres especiales excepto letras, números y guiones
            val sanitizedUsername = username
                .replace(Regex("[^a-zA-Z0-9]"), "")
                .lowercase()
            val email = "${sanitizedUsername}@filmbox.com"

            // Crear usuario en Firebase Auth (await() convierte Task en suspend function)
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            // Obtener el usuario creado o lanzar excepción si falla
            val firebaseUser = authResult.user ?: throw Exception("Error al crear usuario")

            // Crear objeto User con los datos del formulario
            val user = User(
                id = firebaseUser.uid, // UID generado por Firebase Auth
                name = name,
                phoneNumber = phoneNumber,
                userName = username
            )

            // Guardar datos del usuario en Firestore (document ID = UID de Auth)
            usersCollection.document(firebaseUser.uid).set(user).await()

            Result.success(user)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    /**
     * Inicia sesión con username y password
     * @param username Username del usuario
     * @param password Contraseña del usuario
     * @return Result con el User o un error
     */
    suspend fun login(username: String, password: String): Result<User>{
        return  try{
            // Convertir username a formato email
            // Sanitizar el username: remover caracteres especiales excepto letras, números y guiones
            val sanitizedUsername = username
                .replace(Regex("[^a-zA-Z0-9]"), "")
                .lowercase()
            val email = "${sanitizedUsername}@filmbox.com"

            // Iniciar sesión en Firebase Auth con email y contraseña
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("Error al iniciar sesión")

            // Obtener datos completos del usuario desde Firestore
            val userDoc = usersCollection.document(firebaseUser.uid).get().await()

            // Convertir documento Firestore a objeto User
            val user = userDoc.toObject(User::class.java)
                ?: throw Exception("Usuario no encontrado en base de datos")

            Result.success(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    /**
     * Obtiene el usuario actualmente autenticado
     * @return Usuario actual de Firebase Auth o null
     */
    fun getCurrentFirebaseUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Cierra la sesión del usuario actual
     */
    fun logout() {
        return auth.signOut()
    }




}