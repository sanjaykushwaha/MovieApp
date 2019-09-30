package com.sanjay.movieapp.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanjay.movieapp.db.MovieDatabase
import com.sanjay.movieapp.db.dao.MovieDao
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.service.NetworkChecker
import com.sanjay.movieapp.service.RemoteRepository
import com.sanjay.movieapp.service.RetrofitFactory
import com.sanjay.movieapp.service.model.MovieData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieRepository(val context : Context) {


    private var moreResult: Boolean = true
    private var mRemoteRepository: RemoteRepository
    private var allMovies: LiveData<List<Movie>>?
    private var isLoading: Boolean= false
    private val movieDao: MovieDao?



    init {
         val movieDatabase = MovieDatabase.getInstance(context)
         movieDao = movieDatabase?.movieDao()
         mRemoteRepository = RemoteRepository()
         allMovies = movieDao?.getAllMovie()
         loadData()

    }

    fun getAllMovie() : LiveData<List<Movie>>?{
        return allMovies
    }

    fun deleteAllMovies() : Int?{
        return movieDao?.deleteAllMovie()
    }

    fun updateMovie(id: Int, fab: Boolean) : Int?{
        return movieDao?.updateMovie(id, fab)
    }

    private fun loadData(){
        if(NetworkChecker.isInternetAvailable(context)) {
            GlobalScope.launch {
                isLoading = true
                var page = movieDao?.getMaxPageNum()!! + 1
                Log.v("Sanjay", "load Data = " + page)
                if (movieDao?.getCount(page) == 0) {
                    val movieList = getPopularMovies(page)
                    movieList?.forEach {
                        movieDao.insert(Movie(page, it.imdbID, it.Title, it.Type, it.Poster, false))
                    }
                }
                isLoading = false
            }
        }
    }


    private suspend fun getPopularMovies(page : Int) : List<MovieData>?{
        val movieResponse = mRemoteRepository.safeApiCall(
            call = {RetrofitFactory.tmdbApi.getMoviesData("http://www.omdbapi.com/?s=iron&page=${page}&type=movie").await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        if("false".equals(movieResponse?.Response, ignoreCase = true)){
            moreResult = false
        }
        return movieResponse?.Search
    }

    fun isMoreResult() : Boolean{
        return moreResult
    }

    fun loadNextPage() {
        if (isMoreResult()) {
            loadData()
        }
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun dbUnInitialize() {
        MovieDatabase.destroyInstance()
    }
}