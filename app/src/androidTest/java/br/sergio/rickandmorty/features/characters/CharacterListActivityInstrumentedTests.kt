package br.sergio.rickandmorty.features.characters

import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import br.sergio.rickandmorty.features.characters.robot.characterList
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import io.appflate.restmock.RESTMockServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterListActivityInstrumentedTests {
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
        characterList {
            setRESTMockToReturnCharacterListFirstPageWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            RESTMockServer.reset()
            setRESTMockToReturnCharacterListSecondPageWithSuccess()
            setSearchText("tes")
            //todo implement idle resource and remove sleep
            Thread.sleep(2000)
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
