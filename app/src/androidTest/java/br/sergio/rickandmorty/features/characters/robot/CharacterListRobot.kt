package br.sergio.rickandmorty.features.characters.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.robot.BaseTestRobot
import org.hamcrest.Matchers

fun characterList(func: CharacterListRobot.() -> Unit) = CharacterListRobot().apply { func() }

class CharacterListRobot : BaseTestRobot() {
    fun getImageView(itemPosition: Int): ViewInteraction {
        val characterAvatarViewPosition = 0
        return onView(
            Matchers.allOf(
                withId(R.id.character_avatar),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.main_layout),
                        childAtPosition(
                            withId(R.id.recycler_view),
                            itemPosition
                        )
                    ),
                    characterAvatarViewPosition
                ),
                ViewMatchers.isDisplayed()
            )
        )
    }

    fun checkImagePlaceholder(imageView: ViewInteraction): ViewInteraction = imageView.check(
        ViewAssertions.matches(
            drawableIsCorrect(R.drawable.rick_placeholder)
        )
    )

    fun setSearchText(search: String) = typeTextOnEditText(R.id.search_edit_text, search)

    fun checkCharacterNameText(itemPosition: Int, withText: String) =
        matchText(getCharacterNameTextViewFromRecyclerView(itemPosition), withText)

    private fun getCharacterNameTextViewFromRecyclerView(itemPosition: Int): ViewInteraction {
        val characterNameViewPosition = 1

        return onView(
            Matchers.allOf(
                withId(R.id.character_name_text),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.main_layout),
                        childAtPosition(
                            withId(R.id.recycler_view),
                            itemPosition
                        )
                    ),
                    characterNameViewPosition
                ),
                ViewMatchers.isDisplayed()
            )
        )
    }
}