package com.sanjay.movieapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sanjay.movieapp.db.entity.Movie

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovie() : Int

    @Query("SELECT * FROM movie_table ")
    fun getAllMovie(): LiveData<List<Movie>>

    @Query("UPDATE movie_table SET is_fab =:fab WHERE id =:id")
    fun updateMovie(id: Int, fab: Boolean) : Int

    @Query("SELECT count(*) from movie_table where page_num = :page")
    fun getCount(page : Int) : Int

    @Query("select MAX(page_num) from movie_table")
    fun getMaxPageNum() : Int
}