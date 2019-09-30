package com.sanjay.movieapp.service.model

data class MovieResponse(var totalResults : String, var Response : String, var Search : List<MovieData>)
data class MovieData(var Title : String, var Year : String, var imdbID : String, var Type : String, var Poster : String)