package br.sergio.rickandmorty.features.home

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.features.home.robot.home
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import br.sergio.rickandmorty.ui.activities.home.HomeActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeActivityInstrumentedTests {
    private val bodyGuardMortyFilePath = "bodyGuardMorty.json"
    private val successResponseCode = 200
    private val characterUrlPath = "api/character"

    @Rule
    @JvmField
    var mActivityTestRule = IntentsTestRule(HomeActivity::class.java, false, false)

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun shouldLoadCharactersListActivityOnButtonClick() {
        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, bodyGuardMortyFilePath)

        mActivityTestRule.launchActivity(Intent())

        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())

        intending(toPackage(CharactersListActivity::class.java.name)).respondWith(intentResult)

        home {
            clickCharacterListButton()
        }

        intended(allOf(hasComponent(CharactersListActivity::class.java.name)))
    }

    @Test
    fun checkIfHomeFeaturedCharacterTextIsCorrect() {
        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, bodyGuardMortyFilePath)

        mActivityTestRule.launchActivity(Intent())

        home {
            checkCharacterNameText(getTextView(R.id.featured_text), "Featured Character: Body Guard Morty")
        }
    }

    @Test
    fun featuredCharacterImageShouldBeSetAsPlaceholder() {
        RESTMockServer.whenGET(pathContains(characterUrlPath))
            .thenReturnFile(successResponseCode, bodyGuardMortyFilePath)

        mActivityTestRule.launchActivity(Intent())

        home {
            checkImagePlaceholder(getImageView(R.id.character_avatar))
        }
    }
}
