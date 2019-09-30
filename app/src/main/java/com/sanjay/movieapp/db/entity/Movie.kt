package com.sanjay.movieapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class Movie(
    @ColumnInfo(name = "page_num")
    var pageNum: Int,
    @ColumnInfo(name = "imdb_id")
    var imdbID: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "is_fab")
    var isFav: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}