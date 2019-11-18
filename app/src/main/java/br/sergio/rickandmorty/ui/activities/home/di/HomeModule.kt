package br.sergio.rickandmorty.ui.activities.home.di

import br.sergio.rickandmorty.APIInterface
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharacterListViewModelFactory
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
open class HomeModule() {
    @Provides
    open fun providesHomeRepository(apiInterface: APIInterface) =
        HomeRepository(apiInterface)

    @Provides
    open fun providesHomeViewModelFactory(homeRepository: HomeRepository) =
        HomeViewModelFactory(homeRepository)
}