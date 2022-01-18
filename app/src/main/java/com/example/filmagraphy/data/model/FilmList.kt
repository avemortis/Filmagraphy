package com.example.filmagraphy.data.model

import com.example.filmagraphy.data.model.Film
import com.google.gson.annotations.SerializedName

class FilmList (
    @SerializedName("films") val films : List<Film>
)