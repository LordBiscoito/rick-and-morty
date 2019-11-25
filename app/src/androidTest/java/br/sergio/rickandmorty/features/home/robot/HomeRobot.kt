package br.sergio.rickandmorty.features.home.robot

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import br.sergio.rickandmorty.R
import br.sergio.rickandmorty.robot.BaseTestRobot
import br.sergio.rickandmorty.ui.activities.characters.ui.CharactersListActivity
import javax.net.ssl.HttpsURLConnection

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot : BaseTestRobot() {
    fun setRESTMockToReturnBodyGuardMortyWithSuccess() {
        val bodyGuardMortyFilePath = "bodyGuardMorty.json"
        val characterUrlPath = "api/character"

        setRESTMockServerWhenGet(
            characterUrlPath = characterUrlPath,
            responseCode = HttpsURLConnection.HTTP_OK,
            filePath = bodyGuardMortyFilePath
        )
    }

    fun checkIfCharactersListActivityWasDisplayed() =
        checkIntentsIntended(CharactersListActivity::class.java.name)

    fun setCharactersListActivityIntending() =
        setIntendingRespondWith(CharactersListActivity::class.java.name)

    fun checkIfPlaceholderWasSetOnFeaturedCharacterAvatar(): ViewInteraction =
        getViewInteraction(R.id.character_avatar).check(
            ViewAssertions.matches(
                drawableIsCorrect(R.drawable.rick_placeholder)
            )
        )

    fun clickCharacterListButton() = clickButton(R.id.characters_button)

    fun checkIfFeaturedCharacterIsBodyGuardMorty() =
        matchText(getViewInteraction(R.id.featured_text), "Featured Character: Body Guard Morty")
}