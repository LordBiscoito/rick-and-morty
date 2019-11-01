package br.sergio.rickandmorty.api.models

import com.google.gson.annotations.SerializedName


data class CharacterModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: OriginModel,
    @SerializedName("location") val location: OriginModel,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episodeList: ArrayList<String>,
    @SerializedName("url") val requestUrl: String,
    @SerializedName("created") val createdAt: String
)