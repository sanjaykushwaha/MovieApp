package com.sanjay.movieapp.service

import com.sanjay.movieapp.service.model.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface IMDBApi {//QueryMap params : Map<String, String>
    @GET()
    fun getMoviesData(@Url url: String) : Deferred<Response<MovieResponse>>
}