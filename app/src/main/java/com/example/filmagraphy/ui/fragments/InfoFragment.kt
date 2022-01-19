package com.example.filmagraphy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.filmagraphy.R
import com.example.filmagraphy.databinding.FragmentFilmsBinding
import com.example.filmagraphy.databinding.FragmentInfoBinding
import com.example.filmagraphy.presenters.FilmFragmentPresenter
import com.example.filmagraphy.utils.Status
import com.squareup.picasso.Picasso

class InfoFragment : Fragment() {
    private val args: InfoFragmentArgs by navArgs()

    private val presenter = FilmFragmentPresenter()

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        startObserve()
        return binding.root
    }

    private fun startObserve() {
        presenter.getFilms().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SECCESS -> {
                        binding.progressBar.isVisible = false
                        resource.data!!.forEach { film->
                            if (film.id == args.id) {
                                Picasso.with(requireContext())
                                    .load(film.imageUrl)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(binding.imageView3)

                                binding.infoDescription.text = film.description
                                binding.infoName.text = film.name
                                binding.infoRating.text = film.rating.toString()
                                binding.infoYear.text = film.year.toString()
                            }
                        }
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }
}