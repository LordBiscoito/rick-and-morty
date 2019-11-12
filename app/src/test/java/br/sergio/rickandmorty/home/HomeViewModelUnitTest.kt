package br.sergio.rickandmorty.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.ui.activities.characters.repository.HomeRepository
import br.sergio.rickandmorty.ui.activities.home.view_model.HomeViewModel
import com.google.gson.Gson
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.kotlintest.shouldBe
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelUnitTest {
    @get:Rule
    val instaRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockHomeRepository: HomeRepository

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(mockHomeRepository)
    }

    @Test
    fun `check if GetFeaturedCharacter is returning Rick Sanchez`() {
        val characterModel: CharacterModel = Gson().fromJson(
            "{ \"id\": 1, \"name\": \"Rick Sanchez\", \"status\": \"Alive\", \"species\": \"Human\", \"type\": \"\", \"gender\": \"Male\", \"origin\": { \"name\": \"Earth (C-137)\", \"url\": \"https://rickandmortyapi.com/api/location/1\" }, \"location\": { \"name\": \"Earth (Replacement Dimension)\", \"url\": \"https://rickandmortyapi.com/api/location/20\" }, \"image\": \"https://rickandmortyapi.com/api/character/avatar/1.jpeg\", \"episode\": [ \"https://rickandmortyapi.com/api/episode/1\", \"https://rickandmortyapi.com/api/episode/2\", \"https://rickandmortyapi.com/api/episode/3\", \"https://rickandmortyapi.com/api/episode/4\", \"https://rickandmortyapi.com/api/episode/5\", \"https://rickandmortyapi.com/api/episode/6\", \"https://rickandmortyapi.com/api/episode/7\", \"https://rickandmortyapi.com/api/episode/8\", \"https://rickandmortyapi.com/api/episode/9\", \"https://rickandmortyapi.com/api/episode/10\", \"https://rickandmortyapi.com/api/episode/11\", \"https://rickandmortyapi.com/api/episode/12\", \"https://rickandmortyapi.com/api/episode/13\", \"https://rickandmortyapi.com/api/episode/14\", \"https://rickandmortyapi.com/api/episode/15\", \"https://rickandmortyapi.com/api/episode/16\", \"https://rickandmortyapi.com/api/episode/17\", \"https://rickandmortyapi.com/api/episode/18\", \"https://rickandmortyapi.com/api/episode/19\", \"https://rickandmortyapi.com/api/episode/20\", \"https://rickandmortyapi.com/api/episode/21\", \"https://rickandmortyapi.com/api/episode/22\", \"https://rickandmortyapi.com/api/episode/23\", \"https://rickandmortyapi.com/api/episode/24\", \"https://rickandmortyapi.com/api/episode/25\", \"https://rickandmortyapi.com/api/episode/26\", \"https://rickandmortyapi.com/api/episode/27\", \"https://rickandmortyapi.com/api/episode/28\", \"https://rickandmortyapi.com/api/episode/29\", \"https://rickandmortyapi.com/api/episode/30\", \"https://rickandmortyapi.com/api/episode/31\" ], \"url\": \"https://rickandmortyapi.com/api/character/1\", \"created\": \"2017-11-04T18:48:46.250Z\" }",
            CharacterModel::class.java
        )

        Mockito.`when`(mockHomeRepository.getFeaturedCharacter(anyInt()))
            .thenReturn(Single.just(characterModel))

        homeViewModel.getFeaturedCharacter()
        homeViewModel.getFeaturedCharacterMutable().value!!.name shouldBe "Rick Sanchez"
    }

    @Test
    fun checkGetFeaturedCharacterError() {
        val statusCode = 404

        val response: Response<CharacterModel> =
            Response.error(
                statusCode,
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    ""
                )
            )

        val httpException = HttpException(response)
        Mockito.`when`(mockHomeRepository.getFeaturedCharacter(anyInt()))
            .thenReturn(Single.error(httpException))

        homeViewModel.getFeaturedCharacter()
        homeViewModel.onError.value!!.code() shouldBe 404
    }
}