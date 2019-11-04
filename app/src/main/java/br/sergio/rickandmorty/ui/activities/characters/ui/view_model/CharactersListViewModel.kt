package br.sergio.rickandmorty.ui.activities.characters.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.view_models.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class CharactersListViewModel (private var characterRepository: CharacterRepository) :
    BaseViewModel() {

    var hasStoppedPaging: Boolean = false
    private var page: Int = 1

    private val charactersMutableLiveData = MutableLiveData<ArrayList<CharacterModel>>()
    fun getCharactersMutable(): LiveData<ArrayList<CharacterModel>> = charactersMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    fun fetchCharactersByPage() {
        if (hasStoppedPaging) {
            return
        }

        characterRepository.getCharactersByPage(page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                isLoading.postValue(true)
            }.doAfterSuccess {
                isLoading.postValue(false)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                charactersMutableLiveData.postValue(it.results)
                page++
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