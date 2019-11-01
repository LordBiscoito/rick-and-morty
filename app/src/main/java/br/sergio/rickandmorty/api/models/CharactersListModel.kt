package br.sergio.rickandmorty.api.models

import com.google.gson.annotations.SerializedName

data class CharactersListModel(@SerializedName("results") val results: ArrayList<CharacterModel>, val message: String? = "", val error: Throwable? = null )