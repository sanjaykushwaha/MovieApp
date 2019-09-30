package com.sanjay.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.repository.MovieRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MovieViewModelTest {

    @Mock
    private var mRepository: MovieRepository = mock<MovieRepository>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMovieData() {
        val liveData = MutableLiveData<List<Movie>>()
        whenever(mRepository.getAllMovie()).thenReturn(liveData)
        Assert.assertNull(mRepository.getAllMovie()?.value)
    }

    @Test
    fun isLoading() {
        whenever(mRepository.isLoading()).thenReturn(true)
        Assert.assertTrue(mRepository.isLoading())
    }

    @Test
    fun deleteMovies() {
        whenever(mRepository.deleteAllMovies()).thenReturn(1)
        Assert.assertEquals(1, mRepository.deleteAllMovies())
    }

    @Test
    fun updateMovie() {
        whenever(mRepository.updateMovie(anyInt(),anyBoolean())).thenReturn(1)
        Assert.assertEquals(1, mRepository.updateMovie(anyInt(), anyBoolean()))
    }

    @Test
    fun isMoreDataAvailable() {
        whenever(mRepository.isMoreResult()).thenReturn(true)
        Assert.assertTrue(mRepository.isMoreResult())
    }
}