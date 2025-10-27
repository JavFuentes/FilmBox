package android.bootcamp.filmbox.di

import android.bootcamp.filmbox.data.local.MovieDao
import android.bootcamp.filmbox.data.local.MovieDatabase
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Define un módulo para proveer dependencias de la base de datos
@Module
// Instala este módulo en el contenedor Singleton
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Le dice a Hilt cómo crear la base de datos Room
    @Provides
    // Instancia única de la base de datos
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context // Hilt inyecta el contexto de la aplicación
    ): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    // Le dice a Hilt cómo crear el DAO (Hilt inyecta automáticamente MovieDatabase)
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}
