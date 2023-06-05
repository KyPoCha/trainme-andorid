package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.AuthApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import cz.cvut.fit.poliskyr.trainmeapp.util.SessionManager
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val sessionManager: SessionManager) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: AuthApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AuthApiDescription::class.java)

    suspend fun signIn(signInRequest: SignInRequest){
//        val response = apiDescription.signInSystem(signInRequest)
//        val token = response.token
        Log.d("NEW SETED TOKEN FROM API", apiDescription.signInSystem(signInRequest).token)
//        sessionManager.setAccessToken(token)
    }

    suspend fun signUp(signUpRequest: SignUpRequest){
        val response = apiDescription.signUpSystem(signUpRequest)
        Log.d("SIGNUP", "${response.message} is created")
    }
}