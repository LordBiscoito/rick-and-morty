package br.sergio.rickandmorty.ui.activities.characters.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterListViewModelFactory @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CharactersListViewModel(characterRepository) as T
}