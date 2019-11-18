package br.sergio.rickandmorty.di.builder

import br.sergio.rickandmorty.di.scope.PerActivity
import br.sergio.rickandmorty.ui.activities.characters.di.CharcterListModule
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import br.sergio.rickandmorty.ui.activities.home.HomeActivity
import br.sergio.rickandmorty.ui.activities.home.di.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = [CharcterListModule::class])
    internal abstract fun characterListActivityInjector(): CharactersListActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun  homeActivityInjector(): HomeActivity
}