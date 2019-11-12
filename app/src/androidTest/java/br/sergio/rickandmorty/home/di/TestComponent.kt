package br.sergio.rickandmorty.home.di

import br.sergio.rickandmorty.api.RetrofitModule
import br.sergio.rickandmorty.app.MyApplication
import br.sergio.rickandmorty.di.builder.ActivityBuilder
import br.sergio.rickandmorty.di.component.AppComponent
import br.sergio.rickandmorty.home.HomeActivityInstrumentedTests
import br.sergio.rickandmorty.ui.activities.home.di.HomeModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(HomeModule::class)])
interface TestComponent : AppComponent {

    fun inject(homeTest: HomeActivityInstrumentedTests)
}