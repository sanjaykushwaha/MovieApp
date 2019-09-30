package com.sanjay.movieapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.movieapp.MainActivity
import com.sanjay.movieapp.R
import com.sanjay.movieapp.db.entity.Movie
import com.sanjay.movieapp.service.NetworkChecker
import com.sanjay.movieapp.viewmodel.MovieViewModel
import com.sanjay.movieapp.viewmodel.MovieViewModelFactory

class MovieFragment : Fragment() {

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
        createViewModel()
        setupRecyclerView(recyclerView)
        observeData()
        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }


    private fun setupRecyclerView(recyclerView : RecyclerView) {
        val layoutManager = GridLayoutManager(context,3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        var isLoading = movieViewModel.isLoading()
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                    isLoading = true
                    if(NetworkChecker.isInternetAvailable(context!!)) {
                        Toast.makeText(context, "Please wait, Loading...", Toast.LENGTH_SHORT).show()
                        movieViewModel.loadNextPage()
                    }else{
                        Toast.makeText(context, "Network not available", Toast.LENGTH_SHORT).show()
                    }
            }

            override fun isLastPage(): Boolean {
                return !movieViewModel.isMoreDataAvailable()
            }

            override fun isLoading(): Boolean {
                return movieViewModel.isLoading()
            }
        });
    }

    private fun createViewModel(){
        val viewModelFactory = MovieViewModelFactory(activity!!.application)
        movieViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(MovieViewModel::class.java)
    }

    private fun observeData(){
        movieViewModel.getMovieData()?.observe(this,
            Observer<List<Movie>> { list ->
                list?.let {
                    adapter.setNotes(it)
                }
            })
    }
}