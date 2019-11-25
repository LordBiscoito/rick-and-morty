package br.sergio.rickandmorty.robot

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.appflate.restmock.MatchableCall
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

open class BaseTestRobot() {
    protected fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction =
        viewInteraction
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    protected fun typeTextOnEditText(resId: Int, text: String): ViewInteraction =
        onView(withId(resId)).perform(
            ViewActions.replaceText(text),
            ViewActions.closeSoftKeyboard()
        )

    protected fun getViewInteraction(resId: Int): ViewInteraction = onView(withId(resId))

    protected fun clickButton(resId: Int): ViewInteraction =
        onView((withId(resId))).perform(ViewActions.click())

    protected fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
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

    protected fun setRESTMockServerWhenGet(
        characterUrlPath: String,
        responseCode: Int,
        filePath: String
    ): MatchableCall =
        RESTMockServer.whenGET(RequestMatchers.pathContains(characterUrlPath))
            .thenReturnFile(responseCode, filePath)

    protected fun setIntendingRespondWith(packageName: String) {
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        Intents.intending(IntentMatchers.toPackage(packageName))
            .respondWith(intentResult)
    }

    protected fun checkIntentsIntended(packageName: String) =
        Intents.intended(CoreMatchers.allOf(IntentMatchers.hasComponent(packageName)))

    protected fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}