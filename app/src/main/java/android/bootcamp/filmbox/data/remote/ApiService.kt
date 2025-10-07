package android.bootcamp.filmbox.data.remote

import android.bootcamp.filmbox.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    //Endpoint
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") authorization: String,
        @Query("languege") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): MovieResponse
}