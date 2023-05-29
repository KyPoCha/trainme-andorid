package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.AuthApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object AuthDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: AuthApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AuthApiDescription::class.java)

    suspend fun signIn(signInRequest: SignInRequest): String {
        val response = apiDescription.signInSystem(signInRequest)
        token.value = response.token
        return token.value
    }

    suspend fun signUp(signUpRequest: SignUpRequest){
        val response = apiDescription.signUpSystem(signUpRequest)
        Log.d("SIGNUP", "${response.message} is created")
    }

    var token = mutableStateOf("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWQiOiIxIiwiZW1haWwiOiJ0ZXN0QG1haWwuaW8iLCJ1c2VybmFtZSI6InRlc3RAbWFpbC5pbyIsImlhdCI6MTY4NTMwMDE2NCwiZXhwIjoxNjg1OTAwMTY0fQ.bKvcKvEKrGhIPLb5-lpsDY54JzpoaVtQ6p7zxfJZq_Z8u9LGBuwSjOQ0ngyQLHzLbBfepOXuvRJVqLJCkQkjlg")

    fun getToken(): String = token.value

    fun setToken(NewToken: String){
        token.value = NewToken
    }
}