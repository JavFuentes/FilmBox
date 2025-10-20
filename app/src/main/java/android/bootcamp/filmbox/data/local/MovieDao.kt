package android.bootcamp.filmbox.data.local

import android.bootcamp.filmbox.data.model.Movie
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT *  FROM movies ORDER BY vote_average DESC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT *  FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Movie)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMoviesCount(): Int

}