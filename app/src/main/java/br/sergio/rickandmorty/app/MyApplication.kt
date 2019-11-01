package br.sergio.rickandmorty.app

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import br.sergio.rickandmorty.di.AppComponent
import br.sergio.rickandmorty.di.DaggerAppComponent
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharactersListViewModel


class MyApplication : Application() {
    //todo remover o fake dagger, por enquanto só testes
    companion object {
        private lateinit var charactersListViewModel: CharactersListViewModel

        fun fakeDaggerInjectionForCharactersListViewModel() =  CharactersListViewModel(CharacterRepository())
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}