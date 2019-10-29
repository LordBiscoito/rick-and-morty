package br.sergio.rickandmorty.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response

open class BaseViewModel : ViewModel() {
    val onError: MutableLiveData<Response<*>> by lazy {
        MutableLiveData<Response<*>>()
    }

    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val onThrowable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}