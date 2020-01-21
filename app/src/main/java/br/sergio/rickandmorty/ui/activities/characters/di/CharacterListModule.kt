package br.sergio.rickandmorty.ui.activities.characters.di

import br.sergio.rickandmorty.APIInterface
import br.sergio.rickandmorty.app.Constants.CHARACTER_REPOSITORY_SCHEDULER
import br.sergio.rickandmorty.app.Constants.DEBOUNCE_SCHEDULER
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharacterListViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
class CharacterListModule() {
    @Provides
    fun providesCharacterRepository(apiInterface: APIInterface) =
        CharacterRepository(apiInterface)

    @Provides
    fun providesCharacterViewModelFactory(
        characterRepository: CharacterRepository,
        @Named(DEBOUNCE_SCHEDULER) debounceScheduler: Scheduler,
        @Named(CHARACTER_REPOSITORY_SCHEDULER) characterRepositoryScheduler: Scheduler
    ) =
        CharacterListViewModelFactory(
            characterRepository,
            debounceScheduler,
            characterRepositoryScheduler
        )
}