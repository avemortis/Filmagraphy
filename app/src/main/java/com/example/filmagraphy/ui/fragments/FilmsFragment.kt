package com.example.filmagraphy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmagraphy.R
import com.example.filmagraphy.databinding.FragmentFilmsBinding
import com.example.filmagraphy.presenters.FilmFragmentPresenter
import com.example.filmagraphy.ui.adapters.FilmsAdapter
import com.example.filmagraphy.ui.adapters.OnAdapterEventListener
import com.example.filmagraphy.utils.FilmDiffUtils
import com.example.filmagraphy.utils.Status
import com.squareup.picasso.Picasso

class FilmsFragment : Fragment(), OnAdapterEventListener {
    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private val presenter = FilmFragmentPresenter()

    lateinit var adapter: FilmsAdapter

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
        val numberOfFilms = presenter.filmsToShow.size

        val recyclerView = binding.recyclerView
        adapter = FilmsAdapter(numberOfGenres, numberOfFilms, this)
        val manager = GridLayoutManager(requireContext(), 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter.getItemViewType(position) == 3) return 1
                return 2
            }

        }
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    private fun updateRecyclerView() {
        val skip = presenter.genresList.size + 2
        adapter.numberOfFilms = presenter.filmsToShow.size
        binding.recyclerView.adapter?.notifyItemRangeChanged(skip, presenter.filmsToShow.size)
        binding.recyclerView.adapter?.notifyItemRangeRemoved(
            skip + presenter.filmsToShow.size,
            presenter.filmsAll.films.size - presenter.filmsToShow.size
        )
    }

    private fun startObserve() {
        presenter.getFilms().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SECCESS -> {
                        presenter.filmsAll = resource.data!!
                        presenter.filmsToShow = presenter.filmsAll.films
                        presenter.genresCount()
                        binding.progressBar3.isVisible = false
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

    override fun onCreateTextHolder(holder: FilmsAdapter.TextHolder, position: Int) {
        when (position) {
            0 -> holder.text.text = getString(R.string.Genres)
            presenter.genresList.size + 1 -> holder.text.text = getString(R.string.Films)
        }
    }

    override fun onCreateGenreButtonHolder(holder: FilmsAdapter.GenresHolder, position: Int) {
        holder.genreButton.text = presenter.genresList[position - 1]
        holder.genreButton.setOnClickListener {
            val text = holder.genreButton.text.toString()
            presenter.genreClick(text)
            updateRecyclerView()
        }
    }

    override fun onCreateFilmHolder(holder: FilmsAdapter.FilmsHolder, position: Int) {
        val skipPosition = presenter.genresList.size + 2
        val film = presenter.filmsToShow[position - skipPosition]

        Picasso.with(requireContext())
            .load(film.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.poster)
        holder.name.text = film.localName

        holder.itemView.setOnClickListener {
            val action = FilmsFragmentDirections.actionFilmsFragmentToInfoFragment()
            action.id = film.id
            this.findNavController().navigate(action)
        }
    }
}