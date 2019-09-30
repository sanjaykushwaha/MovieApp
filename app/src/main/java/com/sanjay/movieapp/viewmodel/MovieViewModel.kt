package com.sanjay.movieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.repository.MovieRepository
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData



class MovieViewModel(application: Application) : ViewModel() {
    private var allMovies: LiveData<List<Movie>>? = null
    private val repository : MovieRepository = MovieRepository(application)

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val ioScope =  CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        Log.v("sanjay","View model init")
    }
    
    fun getMovieData() : LiveData<List<Movie>>? {
         allMovies   = repository.getAllMovie()
        return allMovies
    }



    fun isLoading() : Boolean {
        return repository.isLoading()
    }


    suspend fun deleteMovies() = withContext(Dispatchers.Default){
        ioScope.launch {
            repository.deleteAllMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.dbUnInitialize()
        viewModelJob.cancel()
    }

    fun updateMovie(id: Int, isFab: Boolean){
        ioScope.launch {
            repository.updateMovie(id, isFab)
        }
    }

    fun loadNextPage(){
        repository.loadNextPage()
    }

    fun isMoreDataAvailable() : Boolean{
        return repository.isMoreResult()
    }

}