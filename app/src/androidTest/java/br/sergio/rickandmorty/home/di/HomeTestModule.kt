package br.sergio.rickandmorty.home.di

import br.sergio.rickandmorty.APIInterface
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.ui.activities.home.di.HomeModule
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModelFactory
import org.mockito.Mock
import org.mockito.Mockito

class HomeTestModule : HomeModule() {
    override fun providesHomeRepository(apiInterface: APIInterface): HomeRepository {
        return Mockito.mock(HomeRepository::class.java)
    }

    override fun providesHomeViewModelFactory(homeRepository: HomeRepository): HomeViewModelFactory {
        return Mockito.mock(HomeViewModelFactory::class.java)
    }
}