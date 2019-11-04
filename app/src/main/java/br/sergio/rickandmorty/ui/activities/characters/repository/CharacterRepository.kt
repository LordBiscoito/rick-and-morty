package br.sergio.rickandmorty.ui.activities.characters.repository

import br.sergio.rickandmorty.APIInterface

class CharacterRepository(private val apiInterface: APIInterface) {
    fun getCharactersByPage(page: Int) = apiInterface.getCharactersByPage(page)
}