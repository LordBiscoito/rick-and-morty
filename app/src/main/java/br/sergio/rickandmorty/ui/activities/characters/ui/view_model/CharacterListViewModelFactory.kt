package br.sergio.rickandmorty.ui.activities.characters.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
class CharacterListViewModelFactory constructor(
    private val characterRepository: CharacterRepository,
    private val debounceScheduler: Scheduler,
    private val characterRepositoryScheduler: Scheduler
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CharactersListViewModel(
            characterRepository,
            debounceScheduler,
            characterRepositoryScheduler
        ) as T
}