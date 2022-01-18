package com.example.filmagraphy.presenters

import androidx.lifecycle.liveData
import com.example.filmagraphy.data.api.FilmService
import com.example.filmagraphy.data.model.FilmList
import com.example.filmagraphy.utils.Resource
import kotlinx.coroutines.Dispatchers

class FilmFragmentPresenter {
    val service = FilmService()
    lateinit var films : FilmList
    var genresList: MutableList<String> = mutableListOf()
    fun getFilms() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(service.getAllFilms()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun genresCount() {
        films.films.forEach { film ->
            film.genres.forEach{ genre->
                if (!genresList.contains(genre)) genresList.add(genre)
            }
        }
    }
}