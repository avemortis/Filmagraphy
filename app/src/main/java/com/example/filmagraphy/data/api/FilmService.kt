package com.example.filmagraphy.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filmagraphy.data.model.FilmList
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FilmService {
    private val filmApi : FilmApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        filmApi = retrofit.create(FilmApi::class.java)
    }

    suspend fun getAllFilms() = filmApi.getFilms()
}