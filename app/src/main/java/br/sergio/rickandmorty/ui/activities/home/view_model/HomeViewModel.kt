package br.sergio.rickandmorty.ui.activities.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.view_models.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class HomeViewModel(private var homeRepository: HomeRepository) :
    BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val featuredCharacterMutableLiveData = MutableLiveData<CharacterModel>()
    fun getFeaturedCharacterMutable(): LiveData<CharacterModel> = featuredCharacterMutableLiveData

    fun getFeaturedCharacter() {
        val id: Int = (0..300).random()

        homeRepository.getFeaturedCharacter(id)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                isLoading.postValue(true)
            }.doAfterSuccess {
                isLoading.postValue(false)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                featuredCharacterMutableLiveData.postValue(it)
            }, onError = {
                isLoading.postValue(false)
                onError.postValue((it as HttpException).response())
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}