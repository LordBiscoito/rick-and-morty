package br.sergio.rickandmorty.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "EspressoIdlingResource"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = countingIdlingResource.increment()
    fun decrement() = countingIdlingResource.decrement()
}