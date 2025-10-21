package android.bootcamp.filmbox.view.home

import android.bootcamp.filmbox.data.model.Movie
import android.bootcamp.filmbox.data.repository.MovieRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = page == 1) }

            repository.getPopularMovies(page).fold(
                onSuccess = { dataSource ->
                    _uiState.update {
                        it.copy(
                            // Si es la página 1, reemplazar; si no, agregar a la lista existente
                            movies = if (page == 1) dataSource.movies
                            else it.movies + dataSource.movies,
                            isLoading = false,
                            errorMessage = null,
                            hasMorePages = page < dataSource.totalPages && !dataSource.isFromCache,
                            currentPage = page,
                            isLoadingMore = false,
                            isFromCache = dataSource.isFromCache
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Error desconocido",
                            isLoadingMore = false
                        )
                    }
                }
            )

        }
    }

    fun loadNextPage() {
        if (!_uiState.value.isLoadingMore &&
            _uiState.value.hasMorePages &&
            !_uiState.value.isFromCache) {
            _uiState.update { it.copy(isLoadingMore = true) }
            loadMovies(_uiState.value.currentPage + 1)
        }
    }

    fun retry() {
        _uiState.update { it.copy(currentPage = 1, isLoadingMore = false) }
        loadMovies()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            repository.refreshMovies().fold(
                onSuccess = { dataSource ->
                    _uiState.update {
                        it.copy(
                            movies = dataSource.movies,
                            isRefreshing = false,
                            errorMessage = null,
                            hasMorePages = dataSource.currentPage < dataSource.totalPages && !dataSource.isFromCache,
                            currentPage = 1,
                            isFromCache = dataSource.isFromCache
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            errorMessage = exception.message ?: "Error al refrescar"
                        )
                    }
                }
            )
        }
    }
}

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasMorePages: Boolean = true,
    val currentPage: Int = 1,
    val isLoadingMore: Boolean = false,
    val isFromCache: Boolean = false,
    val isRefreshing: Boolean = false
)