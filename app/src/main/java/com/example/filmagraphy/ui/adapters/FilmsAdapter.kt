package com.example.filmagraphy.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmagraphy.data.model.Film
import com.example.filmagraphy.databinding.FragmentFilmsItemTextBinding
import com.example.filmagraphy.databinding.FragmentFilmsItemGenreButtonBinding
import com.example.filmagraphy.databinding.FragmentFilmsItemFilmBinding

class FilmsAdapter(
    private val numberOfGenres: Int,
    var numberOfFilms: Int,
    private val listener: OnAdapterEventListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeList: MutableList<ViewType> = mutableListOf()

    init {
        typeList.add(ViewType.TEXT)
        for (i in 0 until numberOfGenres) typeList.add(ViewType.GENRE)
        typeList.add(ViewType.TEXT)
        for (i in 0 until numberOfFilms) typeList.add(ViewType.FILM)
    }

    class TextHolder(binding: FragmentFilmsItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val text = binding.itemText
    }

    class GenresHolder(binding: FragmentFilmsItemGenreButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val genreButton = binding.button
    }

    class FilmsHolder(binding: FragmentFilmsItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val poster = binding.imageView
        val name = binding.textView
    }

    override fun getItemViewType(position: Int): Int {
        return when (typeList[position]) {
            ViewType.TEXT -> 1
            ViewType.GENRE -> 2
            ViewType.FILM -> 3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val binding = FragmentFilmsItemTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TextHolder(binding)
            }
            2 -> {
                val binding = FragmentFilmsItemGenreButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return GenresHolder(binding)
            }
            3 -> {
                val binding = FragmentFilmsItemFilmBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FilmsHolder(binding)
            }
        }
        return TextHolder(FragmentFilmsItemTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                val text : TextHolder = holder as TextHolder
                listener.onCreateTextHolder(text, position)
            }
            2 -> {
                val genre : GenresHolder = holder as GenresHolder
                listener.onCreateGenreButtonHolder(genre, position)
            }
            3 -> {
                val film : FilmsHolder = holder as FilmsHolder
                listener.onCreateFilmHolder(film, position)
            }
        }
    }

    override fun getItemCount() = numberOfGenres + numberOfFilms + 2

}

interface OnAdapterEventListener {
    fun onCreateTextHolder(holder: FilmsAdapter.TextHolder, position: Int)
    fun onCreateGenreButtonHolder(holder: FilmsAdapter.GenresHolder, position: Int)
    fun onCreateFilmHolder(holder: FilmsAdapter.FilmsHolder, position: Int)
}

enum class ViewType {
    TEXT,
    GENRE,
    FILM
}