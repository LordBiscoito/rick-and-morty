package br.sergio.rickandmorty.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.api.models.CharactersListModel
import br.sergio.rickandmorty.ui.activities.characters.repository.CharacterRepository
import br.sergio.rickandmorty.ui.activities.characters.ui.view_model.CharactersListViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.kotlintest.shouldBe
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelUnitTest {

    @get:Rule
    val instaRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockCharacterRepository: CharacterRepository

    private lateinit var characterListViewModel: CharactersListViewModel

    @Before
    fun setUp() {
        characterListViewModel = CharactersListViewModel(mockCharacterRepository)
    }

    @Test
    fun checkGetCharactersByPage() {
        val json =
            "[ { \"id\": 1, \"name\": \"Rick Sanchez\", \"status\": \"Alive\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"Earth (C-137)\", \"url\": \"https://rickandmortyapi.com/api/location/1\" }, \"location\": { \"name\": \"Earth (Replacement Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/20\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/1.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/1\", \"https://rickandmortyapi.com/api/episode/2\", \"https://rickandmortyapi.com/api/episode/3\", \"https://rickandmortyapi.com/api/episode/4\", \"https://rickandmortyapi.com/api/episode/5\", \"https://rickandmortyapi.com/api/episode/6\", \"https://rickandmortyapi.com/api/episode/7\", \"https://rickandmortyapi.com/api/episode/8\", \"https://rickandmortyapi.com/api/episode/9\", \"https://rickandmortyapi.com/api/episode/10\", \"https://rickandmortyapi.com/api/episode/11\", \"https://rickandmortyapi.com/api/episode/12\", \"https://rickandmortyapi.com/api/episode/13\", \"https://rickandmortyapi.com/api/episode/14\", \"https://rickandmortyapi.com/api/episode/15\", \"https://rickandmortyapi.com/api/episode/16\", \"https://rickandmortyapi.com/api/episode/17\", \"https://rickandmortyapi.com/api/episode/18\", \"https://rickandmortyapi.com/api/episode/19\", \"https://rickandmortyapi.com/api/episode/20\", \"https://rickandmortyapi.com/api/episode/21\", \"https://rickandmortyapi.com/api/episode/22\", \"https://rickandmortyapi.com/api/episode/23\", \"https://rickandmortyapi.com/api/episode/24\", \"https://rickandmortyapi.com/api/episode/25\", \"https://rickandmortyapi.com/api/episode/26\", \"https://rickandmortyapi.com/api/episode/27\", \"https://rickandmortyapi.com/api/episode/28\", \"https://rickandmortyapi.com/api/episode/29\", \"https://rickandmortyapi.com/api/episode/30\", \"https://rickandmortyapi.com/api/episode/31\" ], \"url\": \"https://rickandmortyapi.com/api/character/1\", \"created\": \"2017-11-04T18:48:46.250Z\" }, { \"id\": 290, \"name\": \"Rick Sanchez\", \"status\": \"Dead\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"Earth (Evil Rick's Target Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/34\" }, \"location\": { \"name\": \"Earth (Evil Rick's Target Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/34\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/290.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/10\" ], \"url\": \"https://rickandmortyapi.com/api/character/290\", \"created\": \"2017-12-31T20:15:25.716Z\" }, { \"id\": 293, \"name\": \"Rick Sanchez\", \"status\": \"Dead\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"Earth (Replacement Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/20\" }, \"location\": { \"name\": \"Earth (Replacement Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/20\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/293.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/6\" ], \"url\": \"https://rickandmortyapi.com/api/character/293\", \"created\": \"2017-12-31T20:22:29.032Z\" } ]"


        `when`(mockCharacterRepository.getCharactersByPage(1, ""))
            .thenReturn(Single.just(getCharacterList(json)))

        `when`(mockCharacterRepository.getCharactersByPage(2, ""))
            .thenReturn(Single.just(CharactersListModel(ArrayList())))

        characterListViewModel.fetchCharactersByPage()
        characterListViewModel.getCharactersMutable().value!!.size shouldBe 3
        characterListViewModel.getCharactersMutable().value!![0].name shouldBe "Rick Sanchez"

        characterListViewModel.fetchCharactersByPage()
        characterListViewModel.getCharactersMutable().value!!.size shouldBe 0
    }

    @Test
    fun checkGetCharactersByPageWithSearchQuery() {
        val json =
            "[ { \"id\": 44, \"name\": \"Body Guard Morty\", \"status\": \"Dead\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"unknown\", \"url\": \"\" }, \"location\": { \"name\": \"Citadel of Ricks\", \"url\": \"https://rickandmortyapi.com/api/location/3\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/44.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/28\" ], \"url\": \"https://rickandmortyapi.com/api/character/44\", \"created\": \"2017-11-05T10:18:11.062Z\" }, { \"id\": 53, \"name\": \"Blue Shirt Morty\", \"status\": \"unknown\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"unknown\", \"url\": \"\" }, \"location\": { \"name\": \"Citadel of Ricks\", \"url\": \"https://rickandmortyapi.com/api/location/3\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/53.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/10\" ], \"url\": \"https://rickandmortyapi.com/api/character/53\", \"created\": \"2017-11-05T11:28:38.627Z\" } ]"
        val queryString = "Morty"

        `when`(mockCharacterRepository.getCharactersByPage(1, queryString))
            .thenReturn(Single.just(getCharacterList(json)))

        characterListViewModel.searchQuery = queryString
        characterListViewModel.fetchCharactersByPage()
        characterListViewModel.getCharactersMutable().value!!.size shouldBe 2
        characterListViewModel.getCharactersMutable().value!![0].name shouldBe "Body Guard Morty"
    }

    @Test
    fun checkGetCharacterByPageError() {
        val statusCode = 404
        val queryString = "Not a norma string"

        val response: Response<CharacterModel> =
            Response.error(
                statusCode,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    ""
                )
            )

        val httpException = HttpException(response)
        `when`(mockCharacterRepository.getCharactersByPage(1, queryString))
            .thenReturn(Single.error(httpException))

        characterListViewModel.searchQuery = queryString
        characterListViewModel.fetchCharactersByPage()
        characterListViewModel.onError.value!!.code() shouldBe 404
    }

    private fun getCharacterList(json: String): CharactersListModel {
        return CharactersListModel(
            Gson().fromJson(
                json,
                object : TypeToken<ArrayList<CharacterModel>>() {}.type
            )
        )
    }
}