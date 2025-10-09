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

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = page == 1) }
            try {
                val response = RetrofitClient.apiService.getPopularMovies(
                    authorization = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}",
                    page = page
                )

                _uiState.update {
                    it.copy(
                        // Si es la página 1, reemplazar; si no, agregar a la lista existente
                        movies = if (page == 1) response.results
                        else it.movies + response.results,
                        isLoading = false,
                        errorMessage = null,
                        hasMorePages = page < response.totalPages,
                        currentPage = page,
                        isLoadingMore = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        if (!_uiState.value.isLoadingMore && _uiState.value.hasMorePages) {
            _uiState.update { it.copy(isLoadingMore = true) }
            loadMovies(_uiState.value.currentPage + 1)
        }
    }

    fun retry() {
        _uiState.update { it.copy(currentPage = 1, isLoadingMore = false) }
        loadMovies()
    }
}

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasMorePages: Boolean = true,
    val currentPage: Int = 1,
    val isLoadingMore: Boolean = false,
)