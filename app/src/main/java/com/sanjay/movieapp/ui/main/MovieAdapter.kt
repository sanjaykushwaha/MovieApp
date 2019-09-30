package com.sanjay.movieapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sanjay.movieapp.R
import com.sanjay.movieapp.db.entity.Movie

class MovieAdapter(val onFavSelectedListener: (isSelected : Boolean, id: Int) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    private var movies: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentNote = movies[position]
        if(currentNote.isFav) {
            holder.fabButton.setImageResource(R.drawable.ic_heart_selected)
        }else{
            holder.fabButton.setImageResource(R.drawable.ic_heart_unselected)
        }
        holder.fabButton.setOnClickListener {
            onFavSelectedListener.invoke(!currentNote.isFav, currentNote.id)
        }
        val myOptions = RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.no_image)
        Glide.with(holder.coverPhoto)
            .load(currentNote.url)
            .apply(myOptions)
            .into(holder.coverPhoto)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setNotes(notes: List<Movie>) {
        this.movies = notes
        notifyDataSetChanged()
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fabButton: ImageButton = itemView.findViewById(R.id.favButton)
        var coverPhoto: ImageView = itemView.findViewById(R.id.imageView)

    }
}