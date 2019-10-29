package br.sergio.rickandmorty.di

import br.sergio.rickandmorty.app.RickAndMortyApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

//@Singleton
//@Component(modules = [(AndroidSupportInjectionModule::class)])
//interface ApplicationComponent : AndroidInjector<RickAndMortyApplication> {
//    @Component.Factory
//    abstract class Builder : AndroidInjector.Factory<RickAndMortyApplication>
//}