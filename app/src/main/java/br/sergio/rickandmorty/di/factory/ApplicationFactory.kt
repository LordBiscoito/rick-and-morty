package br.sergio.rickandmorty.di.factory

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component
interface ApplicationFactory {
    @Component.Factory
    interface Factory {
        fun bindsApplication(@BindsInstance applicationContext: Application): ApplicationFactory
    }
}