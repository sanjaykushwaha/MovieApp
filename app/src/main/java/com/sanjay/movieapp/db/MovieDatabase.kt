package com.sanjay.movieapp.db

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.db.dao.MovieDao

@Database(entities = [Movie::class], version = 1, exportSchema = true)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var mInstance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {

            if (mInstance == null) {
                synchronized(MovieDatabase::class) {
                    mInstance = Room.databaseBuilder(context.applicationContext,
                            MovieDatabase::class.java, "movie_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build()
                }
            }
            return mInstance
        }

        fun destroyInstance() {
            mInstance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.v("sanjay", "db created...")
            }
        }
    }

}


