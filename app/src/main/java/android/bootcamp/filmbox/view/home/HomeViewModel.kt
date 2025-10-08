package android.bootcamp.filmbox.view.home

import android.bootcamp.filmbox.BuildConfig
import android.bootcamp.filmbox.data.model.Movie
import android.bootcamp.filmbox.data.remote.RetrofitClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy( isLoading = true) }
            try {
                val response = RetrofitClient.apiService.getPopularMovies(
                    authorization = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}"
                )

                _uiState.update{
                    it.copy(
                        movies = response.results,
                        isLoading = false,
                        errorMessage = null
                    )
                }


            } catch (e: Exception){
                _uiState.update{
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido"
                    )
                }
            }

        }
    }

    fun retry(){
        loadMovies()
    }
}

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)