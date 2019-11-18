package br.sergio.rickandmorty.ui.activities.characters.ui.view_model

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.api.models.CharactersListModel
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.utils.RxSearchObservable
import br.sergio.rickandmorty.view_models.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CharactersListViewModel(private var characterRepository: CharacterRepository) :
    BaseViewModel() {

    var hasStoppedPaging: Boolean = false
    private var page: Int = 1

    var searchQuery: String = ""

    private val charactersMutableLiveData = MutableLiveData<ArrayList<CharacterModel>>()
    fun getCharactersMutable(): LiveData<ArrayList<CharacterModel>> = charactersMutableLiveData

    private val searchMutableLiveData = MutableLiveData<ArrayList<CharacterModel>>()
    fun getSearchMutable(): LiveData<ArrayList<CharacterModel>> = searchMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    fun fetchCharactersByPage() {
        if (hasStoppedPaging) {
            return
        }

        characterRepository.getCharactersByPage(page, searchQuery)
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

    fun searchCharactersByName(editText: EditText) {
        RxSearchObservable.fromView(editText).debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                searchQuery = it

                characterRepository.getCharactersByName(searchQuery).subscribeOn(Schedulers.io()).doOnError {
                    onError.postValue((it as HttpException).response())
                }.onErrorReturn {
                    CharactersListModel(ArrayList())
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                page = 1
                hasStoppedPaging = false
                searchMutableLiveData.postValue(it.results)
            }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}