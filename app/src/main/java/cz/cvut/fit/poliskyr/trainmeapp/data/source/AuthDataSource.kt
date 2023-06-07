package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.AuthApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.BadGatewayException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.ForbiddenException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.UnauthorizedException
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
        try {
//            val response = apiDescription.signInSystem(signInRequest)
//            val token = response.token
            Log.d("NEW SETED TOKEN FROM API", apiDescription.signInSystem(signInRequest).token)
//            sessionManager.setAccessToken(token)
        }
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Forbidden request from Auth service")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Unauthorized request from Auth service")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: BadGateWay request from Auth service")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("Error: from Auth service")
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest){
        try {
            val response = apiDescription.signUpSystem(signUpRequest)
            Log.d("SIGNUP", "${response.message} is created")
        }
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
        }
    }
}