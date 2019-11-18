package br.sergio.rickandmorty.di.component

import br.sergio.rickandmorty.api.RetrofitModule
import br.sergio.rickandmorty.app.MyApplication
import br.sergio.rickandmorty.app.MyApplicationTest
import br.sergio.rickandmorty.di.builder.ActivityBuilder
import br.sergio.rickandmorty.network.di.RetrofitTestModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (RetrofitTestModule::class)])
interface AppTestComponent : AndroidInjector<MyApplicationTest> {
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MyApplicationTest>
}