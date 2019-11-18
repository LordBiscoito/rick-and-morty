package br.sergio.rickandmorty.app

import br.sergio.rickandmorty.di.component.DaggerAppComponent
import br.sergio.rickandmorty.di.component.DaggerAppTestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplicationTest : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out MyApplicationTest> =
        DaggerAppTestComponent.factory().create(this)
}