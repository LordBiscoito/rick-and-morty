package br.sergio.rickandmorty.features.samples

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.ui.activities.samples.SimpleEspressoIdlingResourceSampleActivity
import br.sergio.rickandmorty.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SimpleEspressoIdlingResourceInstrumentedTest  {
    @get:Rule
    val activityRule = ActivityScenarioRule(SimpleEspressoIdlingResourceSampleActivity::class.java)

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test() {
        val context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        val testText: String = context.getString(R.string.text_was_changed)
        onView(withId(R.id.idleText)).check(matches(ViewMatchers.withText(testText)))
    }
}