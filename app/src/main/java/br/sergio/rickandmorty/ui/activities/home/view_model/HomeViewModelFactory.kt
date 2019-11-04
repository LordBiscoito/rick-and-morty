package br.sergio.rickandmorty.ui.activities.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModelFactory @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HomeViewModel(homeRepository) as T
}