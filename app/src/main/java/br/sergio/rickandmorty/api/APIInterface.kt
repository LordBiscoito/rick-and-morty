package br.sergio.rickandmorty

import br.sergio.rickandmorty.api.models.CharacterModel
import br.sergio.rickandmorty.api.models.CharactersListModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
    @GET("api/character")
    fun getCharactersByPage(@Query("page") page: Int, @Query("name") name: String):  Single<CharactersListModel>

    @GET("api/character")
    fun getCharactersByName(@Query("name") name: String):  Observable<CharactersListModel>

    @GET("api/character/{id}")
    fun getFeaturedCharacter(@Path("id") id: Int):  Single<CharacterModel>
}