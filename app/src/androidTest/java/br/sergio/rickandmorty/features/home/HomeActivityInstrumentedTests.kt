package br.sergio.rickandmorty.features.home

import android.content.Intent
import androidx.test.espresso.intent.rule.IntentsTestRule
import br.sergio.rickandmorty.features.home.robot.home
import br.sergio.rickandmorty.ui.activities.home.HomeActivity
import io.appflate.restmock.RESTMockServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeActivityInstrumentedTests {
    @get:Rule
    var mActivityTestRule = IntentsTestRule(HomeActivity::class.java, false, false)

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun shouldLoadCharactersListActivityOnButtonClick() {
        home {
            setRESTMockToReturnBodyGuardMortyWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            setCharactersListActivityIntending()
            clickCharacterListButton()
            checkIfCharactersListActivityWasDisplayed()
        }
    }

    @Test
    fun checkIfHomeFeaturedCharacterTextIsCorrect() {
        home {
            setRESTMockToReturnBodyGuardMortyWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            checkIfFeaturedCharacterIsBodyGuardMorty()
        }
    }

    @Test
    fun featuredCharacterImageShouldBeSetAsPlaceholder() {
        home {
            setRESTMockToReturnBodyGuardMortyWithSuccess()
            mActivityTestRule.launchActivity(Intent())
            checkIfPlaceholderWasSetOnFeaturedCharacterAvatar()
        }
    }
}
