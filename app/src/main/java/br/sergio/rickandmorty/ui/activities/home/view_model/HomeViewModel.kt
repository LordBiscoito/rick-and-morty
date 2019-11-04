package br.sergio.rickandmorty.ui.activities.home.view_model

import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.view_models.BaseViewModel
import io.reactivex.disposables.CompositeDisposable


class HomeViewModel(private var homeRepository: HomeRepository) :
    BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}