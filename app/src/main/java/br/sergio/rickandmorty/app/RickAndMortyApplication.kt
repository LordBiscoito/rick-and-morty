package br.sergio.rickandmorty.app

import android.app.Application


class RickAndMortyApplication : Application() {

}

//class RickAndMortyApplication : Application(), HasAndroidInjector {
//    @Inject
//    @Volatile
//    lateinit var androidInjector: DispatchingAndroidInjector<Any>
//
//    override fun androidInjector(): AndroidInjector<Any> {
//        return androidInjector
//    }
//}