package com.example.filmagraphy.data.model

import com.google.gson.annotations.SerializedName

class Film (
    @SerializedName("id") val id: Int,
    @SerializedName("localized_name") val localName: String,
    @SerializedName("name") val name: String
)