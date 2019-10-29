package br.sergio.rickandmorty

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

import java.io.IOException

object APIUtils {
    fun errorResponse(context: Context, response: Response<*>) {
        var errorMessage = ""
        try {
            errorMessage = JSONObject(response.errorBody()?.string()).getString("error")
        } catch (e: JSONException) {
            errorMessage = e.message!!
        } catch (e: IOException) {
            errorMessage = e.message!!
        }

        Utils.showSimpleMessage(context, "Code ${response.code()}", errorMessage)
    }

    fun errorResponseWithThrowable(context: Context, errorMessage: String) {
        Utils.showSimpleMessage(context, context.getString(R.string.api_error_text), errorMessage)
    }
}