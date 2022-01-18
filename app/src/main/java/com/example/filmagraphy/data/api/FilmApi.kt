package com.example.filmagraphy.data.api

import com.example.filmagraphy.data.model.Film
import com.example.filmagraphy.data.model.FilmList
import retrofit2.Response
import retrofit2.http.GET

interface FilmApi {
    @GET("sequeniatesttask/films.json")
    suspend fun getFilms() : FilmList
}