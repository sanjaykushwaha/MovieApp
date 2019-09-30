package com.sanjay.movieapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.movieapp.MainActivity
import com.sanjay.movieapp.R
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.viewmodel.MovieViewModel
import com.sanjay.movieapp.viewmodel.MovieViewModelFactory
import kotlin.coroutines.coroutineContext

class FavouriteMovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView : RecyclerView = root.findViewById(R.id.recycler)
        adapter = MovieAdapter { isSelected, id ->
                movieViewModel.updateMovie(id, isSelected)
        }
        setupRecyclerView(recyclerView)
        createViewModel()
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(): FavouriteMovieFragment {
            return FavouriteMovieFragment()
        }
    }


    private fun setupRecyclerView(recyclerView : RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(context,3)
        recyclerView.adapter = adapter
    }

    private fun createViewModel(){
        val viewModelFactory = MovieViewModelFactory(activity!!.application)
        movieViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(MovieViewModel::class.java)
        movieViewModel.getMovieData()?.observe(this,
                Observer<List<Movie>> { list ->
                    list?.let {
                        adapter.setNotes(it.filter{
                            it.isFav
                        })
                    }
                })
    }

}