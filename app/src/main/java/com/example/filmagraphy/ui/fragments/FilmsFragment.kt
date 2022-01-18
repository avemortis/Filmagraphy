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
import com.example.filmagraphy.ui.adapters.FilmsAdapter
import com.example.filmagraphy.ui.adapters.OnAdapterEventListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FilmsFragment : Fragment(), OnAdapterEventListener {
    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)

        setRecyclerView()

        MainScope().launch {
            val films = FilmService().getAllFilms()
        }

        return binding.root
    }

    private fun setRecyclerView() {
        val recyclerView = binding.recyclerView
        val adapter = FilmsAdapter(40, 8, this)
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

    companion object {

    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateTextHolder(holder: FilmsAdapter.TextHolder, position: Int) {
        holder.text.text = "Test"
    }

    override fun onCreateGenreButtonHolder(holder: FilmsAdapter.GenresHolder, position: Int) {
        holder.genreButton.text = "Test"
    }

    override fun onCreateFilmHolder(holder: FilmsAdapter.FilmsHolder, position: Int) {
        Picasso.with(requireContext())
            .load("https://avatars.githubusercontent.com/u/51025544?v=4")
            .into(holder.poster)
        holder.name.text = "Test"
    }
}