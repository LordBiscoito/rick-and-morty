package br.sergio.rickandmorty.ui.activities.characters.repository

import br.sergio.rickandmorty.APIClient

class CharacterRepository() {
    fun getCharactersByPage(page: Int) = APIClient.apiInterface().getCharactersByPage(page)
}