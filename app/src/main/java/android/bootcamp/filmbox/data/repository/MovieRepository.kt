package android.bootcamp.filmbox.data.repository

import android.bootcamp.filmbox.BuildConfig
import android.bootcamp.filmbox.data.local.MovieDao
import android.bootcamp.filmbox.data.model.Movie
import android.bootcamp.filmbox.data.remote.ApiService

class MovieRepository(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
) {
    /**
     * Obtiene películas populares con estrategia network-first con fallback a cache.
     * 1. Intenta cargar desde la API
     * 2. Si tiene éxito, guarda en Room y retorna los datos
     * 3. Si falla, carga desde Room (cache local)
     */

    suspend fun getPopularMovies(page: Int = 1): Result<MovieDataSource> {
        return try {
            // Intentar cargar desde la API
            val response = apiService.getPopularMovies(
                authorization = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}",
                page = page
            )

            // Guardar en la cache local (solo una página para simplificar)
            if (page == 1) {
                movieDao.deleteAllMovies()
                movieDao.insertMovies(response.results)
            } else {
                //Para el resto de páginas solo agregar a la DB
                movieDao.insertMovies(response.results)
            }

            Result.success(
                MovieDataSource(
                    movies = response.results,
                    totalPages = response.totalPages,
                    currentPage = response.page,
                    isFromCache = false
                )
            )

        } catch (e: Exception) {
            // Si falla la API, intentar cargar desde la cache
            val cachedMovies = movieDao.getAllMovies()

            if(cachedMovies.isNotEmpty()){
                Result.success(
                    MovieDataSource(
                        movies = cachedMovies,
                        totalPages = 1,
                        currentPage = 1,
                        isFromCache = true
                    )
                )
            } else {
                // No hay cache, retornar error
                Result.failure(e)
            }
        }
    }

    suspend fun refreshMovies(): Result<MovieDataSource> {
        return getPopularMovies(page = 1)
    }
}

data class MovieDataSource(
    val movies: List<Movie>,
    val totalPages: Int,
    val currentPage: Int,
    val isFromCache: Boolean,
)