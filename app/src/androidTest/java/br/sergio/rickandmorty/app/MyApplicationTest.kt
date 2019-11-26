package br.sergio.rickandmorty.app

import android.app.Application
import br.sergio.rickandmorty.di.component.DaggerAppTestComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplicationTest : Application(), HasAndroidInjector {
    @Inject
    lateinit var myInjector: DispatchingAndroidInjector<Any>

    var component = DaggerAppTestComponent.builder().build()

    override fun onCreate() {
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return myInjector
    }
}