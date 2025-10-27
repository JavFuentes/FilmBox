package android.bootcamp.filmbox.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Define un módulo para proveer dependencias de Firebase
@Module
// Instala este módulo en el contenedor Singleton
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    // Le dice a Hilt cómo obtener la instancia de FirebaseAuth
    @Provides
    // Instancia única de FirebaseAuth
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // Le dice a Hilt cómo obtener la instancia de Firestore
    @Provides
    // Instancia única de Firestore
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
