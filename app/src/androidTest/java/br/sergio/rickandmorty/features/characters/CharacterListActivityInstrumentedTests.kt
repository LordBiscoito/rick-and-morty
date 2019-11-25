package br.sergio.rickandmorty.features.characters

import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import br.sergio.rickandmorty.features.characters.robot.characterList
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterListActivityInstrumentedTests {
    private val characterListFirstPage = "characterListFirstPage.json"
    private var characterListSecondPage = "characterListSecondPage.json"
    private val successResponseCode = 200
    private val characterUrlPath = "api/character"

    @get:Rule
    var mActivityTestRule = IntentsTestRule(CharactersListActivity::class.java, false, false)

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun characterListFirstItemShouldBeRickSanchez() {
        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, characterListFirstPage)

        mActivityTestRule.launchActivity(Intent())
        characterList {
            checkCharacterNameText(0, "Name: Rick Sanchez")
        }
    }

    @Test
    fun characterListSecondPageItemShouldBeAquaMorty() {
        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, characterListFirstPage)

        mActivityTestRule.launchActivity(Intent())

        RESTMockServer.reset()
        RESTMockServer.whenGET(pathContains(characterUrlPath + "?name="))
            .thenReturnFile(successResponseCode, characterListSecondPage)

        characterList {
            setSearchText("tes")
            //todo implement idle resource and remove sleep
            Thread.sleep(2000)
            checkCharacterNameText(0, "Name: Aqua Morty")
        }
    }

    @Test
    fun characterListSecondItemImageShouldBeSetAsPlaceholder() {
        val recycleViewTestItemPosition = 1

        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, characterListFirstPage)

        mActivityTestRule.launchActivity(Intent())
        characterList {
            checkImagePlaceholder(
                getImageView(recycleViewTestItemPosition)
            )
        }
    }
}
