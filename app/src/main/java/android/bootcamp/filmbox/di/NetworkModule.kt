package android.bootcamp.filmbox.di

import android.bootcamp.filmbox.BuildConfig
import android.bootcamp.filmbox.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Define un módulo que contiene métodos @Provides para crear dependencias
@Module
// Instala este módulo en el contenedor Singleton (vive toda la vida de la app)
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // Le dice a Hilt cómo crear esta dependencia manualmente
    @Provides
    // Esta instancia será única en toda la app (patrón Singleton)
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // Le dice a Hilt cómo crear Retrofit (Hilt inyecta automáticamente OkHttpClient)
    @Provides
    // Instancia única de Retrofit
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Le dice a Hilt cómo crear ApiService (Hilt inyecta automáticamente Retrofit)
    @Provides
    // Instancia única de ApiService
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
