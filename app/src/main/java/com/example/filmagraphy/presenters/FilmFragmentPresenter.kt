package com.example.filmagraphy.presenters

import android.os.Bundle
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
    var filmsToShow: List<Film> = emptyList()
    var genresList: MutableList<String> = mutableListOf()


    fun getFilms() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            load()
            emit(Resource.success(filmsToShow))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    private suspend fun load(){
        filmsAll = service.getAllFilms()
        filmsToShow = filmsAll.films
        genresCount()
    }

    fun genreClick(genre : String) {
        val newList : MutableList<Film> = mutableListOf()
        filmsAll.films.forEach { film->
            if (film.genres.contains(genre)) newList.add(film)
        }
        filmsToShow = newList
    }

    fun genresCount() {
        filmsAll.films.forEach { film ->
            film.genres.forEach{ genre->
                if (!genresList.contains(genre)) genresList.add(genre)
            }
        }
    }

    companion object {
        val GENRE_TAG = "GENRE"
    }
}