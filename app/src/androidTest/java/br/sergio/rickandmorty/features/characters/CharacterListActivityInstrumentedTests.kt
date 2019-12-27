package br.sergio.rickandmorty.features.characters

import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.platform.app.InstrumentationRegistry
import br.sergio.rickandmorty.app.Constants.DEBOUNCE_DELAY_TIME
import br.sergio.rickandmorty.app.Constants.DEBOUNCE_SCHEDULER
import br.sergio.rickandmorty.app.MyApplicationTest
import br.sergio.rickandmorty.features.characters.robot.characterList
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import io.appflate.restmock.RESTMockServer
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@RunWith(MockitoJUnitRunner::class)
class CharacterListActivityInstrumentedTests {

    @Inject
    @Named(DEBOUNCE_SCHEDULER)
    lateinit var scheduler: Scheduler

    @get:Rule
    var mActivityTestRule = IntentsTestRule(CharactersListActivity::class.java, false, false)

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun characterListFirstItemShouldBeRickSanchez() {
        characterList {
            setRESTMockToReturnCharacterListFirstPageWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            checkCharacterNameText(0, "Name: Rick Sanchez")
        }
    }

    @Test
    fun characterListSecondPageItemShouldBeAquaMorty() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplicationTest
        app.component.inject(this)

        characterList {
            setRESTMockToReturnCharacterListFirstPageWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            RESTMockServer.reset()
            setRESTMockToReturnCharacterListSecondPageWithSuccess()
            setSearchText("Aqua Morty")
            (scheduler as TestScheduler).advanceTimeBy(DEBOUNCE_DELAY_TIME, TimeUnit.MILLISECONDS)
            checkCharacterNameText(0, "Name: Aqua Morty")
        }
    }

    @Test
    fun characterListSecondItemImageShouldBeSetAsPlaceholder() {
        characterList {
            setRESTMockToReturnCharacterListFirstPageWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            checkIfPlaceholderWasSetOnItemAtPosition1()
        }
    }
}
