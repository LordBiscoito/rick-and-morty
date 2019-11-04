package br.sergio.rickandmorty.ui.activities.characters.di

import br.sergio.rickandmorty.APIInterface
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharacterListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CharcterListModule() {
    @Provides
    fun providesCharacterRepository(apiInterface: APIInterface) =
        CharacterRepository(apiInterface)

    @Provides
    fun providesCharacterViewModelFactory(characterRepository: CharacterRepository) =
        CharacterListViewModelFactory(characterRepository)
}