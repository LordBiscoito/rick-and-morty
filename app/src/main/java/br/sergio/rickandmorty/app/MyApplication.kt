package br.sergio.rickandmorty.app

import br.sergio.rickandmorty.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


open class MyApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out MyApplication> =
        DaggerAppComponent.factory().create(this)
}