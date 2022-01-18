package com.example.filmagraphy.presenters

import android.util.Log
import androidx.lifecycle.liveData
import com.example.filmagraphy.data.api.FilmService
import com.example.filmagraphy.data.model.Film
import com.example.filmagraphy.data.model.FilmList
import com.example.filmagraphy.utils.Resource
import kotlinx.coroutines.Dispatchers

class FilmFragmentPresenter {
    val service = FilmService()
    lateinit var filmsAll : FilmList
    lateinit var filmsToShow: List<Film>
    var genresList: MutableList<String> = mutableListOf()
    fun getFilms() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(service.getAllFilms()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun genreClick(genre : String) {
        val newList : MutableList<Film> = mutableListOf()
        filmsAll.films.forEach { film->
            if (film.genres.contains(genre)) newList.add(film)
        }
        filmsToShow = newList
        Log.d("1", "1")
    }

    fun genresCount() {
        filmsAll.films.forEach { film ->
            film.genres.forEach{ genre->
                if (!genresList.contains(genre)) genresList.add(genre)
            }
        }
    }
}