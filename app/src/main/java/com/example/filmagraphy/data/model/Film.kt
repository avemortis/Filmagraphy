package com.example.filmagraphy.data.model

import android.accounts.AuthenticatorDescription
import com.google.gson.annotations.SerializedName

class Film (
    @SerializedName("id") val id: Int,
    @SerializedName("localized_name") val localName: String,
    @SerializedName("name") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("rating") val rating: Float,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("genres") val genres: List<String>
)