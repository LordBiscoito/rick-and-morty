package br.sergio.rickandmorty.features.home.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.robot.BaseTestRobot

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot : BaseTestRobot() {
    fun getImageView(resId: Int): ViewInteraction = onView(ViewMatchers.withId(resId))

    fun getTextView(resId: Int): ViewInteraction = onView(ViewMatchers.withId(resId))

    fun checkImagePlaceholder(imageView: ViewInteraction): ViewInteraction = imageView.check(
        ViewAssertions.matches(
            drawableIsCorrect(R.drawable.rick_placeholder)
        )
    )

    fun clickCharacterListButton() = clickButton(R.id.characters_button)

    fun checkCharacterNameText(textView: ViewInteraction, withText: String) =
        matchText(textView, withText)
}