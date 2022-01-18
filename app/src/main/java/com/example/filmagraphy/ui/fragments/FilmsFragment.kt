package com.example.filmagraphy.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmagraphy.R
import com.example.filmagraphy.data.api.FilmService
import com.example.filmagraphy.data.model.Film
import com.example.filmagraphy.databinding.FragmentFilmsBinding
import com.example.filmagraphy.presenters.FilmFragmentPresenter
import com.example.filmagraphy.ui.adapters.FilmsAdapter
import com.example.filmagraphy.ui.adapters.OnAdapterEventListener
import com.example.filmagraphy.utils.Status
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FilmsFragment : Fragment(), OnAdapterEventListener {
    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val presenter = FilmFragmentPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        startObserve()
        return binding.root
    }

    private fun setRecyclerView() {
        val numberOfGenres = presenter.genresList.size
        val numberOfFilms = presenter.films.films.size

        val recyclerView = binding.recyclerView
        val adapter = FilmsAdapter(numberOfGenres, numberOfFilms, this)
        val manager = GridLayoutManager(requireContext(), 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter.getItemViewType(position)==3) return 1
                return 2
            }

        }
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    private fun startObserve() {
        presenter.getFilms().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SECCESS -> {
                        presenter.films = resource.data!!
                        presenter.genresCount()
                        setRecyclerView()
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    companion object {
        val TAG = "FilmFragment"
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateTextHolder(holder: FilmsAdapter.TextHolder, position: Int) {
        when (position) {
            0 -> holder.text.text = getString(R.string.Genres)
            presenter.genresList.size + 1 -> holder.text.text = getString(R.string.Films)
        }
    }

    override fun onCreateGenreButtonHolder(holder: FilmsAdapter.GenresHolder, position: Int) {
        holder.genreButton.text = presenter.genresList[position - 1]
    }

    override fun onCreateFilmHolder(holder: FilmsAdapter.FilmsHolder, position: Int) {
        val skipPosition = presenter.genresList.size + 2
        val film = presenter.films.films[position - skipPosition]

        Picasso.with(requireContext())
            .load(film.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.poster)
        holder.name.text = film.localName
    }
}