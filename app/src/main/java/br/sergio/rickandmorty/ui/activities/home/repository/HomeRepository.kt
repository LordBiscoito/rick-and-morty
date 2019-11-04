package br.sergio.rickandmorty.ui.activities.characters.repository

import br.sergio.rickandmorty.APIInterface


class HomeRepository(private val apiInterface: APIInterface) {
    fun getFeaturedCharacter(id: Int) = apiInterface.getFeaturedCharacter(id)
}