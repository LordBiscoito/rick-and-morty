package br.sergio.rickandmorty.home

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
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import br.sergio.rickandmorty.ui.activities.home.HomeActivity
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

    @Rule
    @JvmField
    var mActivityTestRule = IntentsTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun shouldSendToCharactersListActivityWhenButtonClicked() {
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())

        intending(toPackage(CharactersListActivity::class.java.name)).respondWith(intentResult)
        onView(withId(R.id.characters_button)).check(matches(isDisplayed()))
            .perform(click())

        intended(allOf(hasComponent(CharactersListActivity::class.java.name)))
    }

    @Test
    fun homeFeaturedCharacterTextIsCorrect() {
        onView(withId(R.id.featured_text))
            .check(matches(withText(containsString("Featured Character:"))))
    }

    @Test
    fun checkFeaturedCharacterImagePlaceholder() {
        val imageView = onView(withId(R.id.character_avatar))

        imageView.check(matches(drawableIsCorrect(R.drawable.rick_placeholder)))
    }

    private fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with drawable from resource id: ")
                description.appendValue(drawableResId)
            }

            override fun matchesSafely(target: View?): Boolean {
                if (target !is ImageView) {
                    return false
                }
                if (drawableResId < 0) {
                    return target.drawable == null
                }
                val expectedDrawable = ContextCompat.getDrawable(target.context, drawableResId)
                    ?: return false

                val bitmap = (target.drawable as BitmapDrawable).bitmap
                val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
                return bitmap.sameAs(otherBitmap)
            }
        }
    }
}
