package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.User
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.UserApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.BadGatewayException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.ForbiddenException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.UnauthorizedException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apiInterceptor: ApiInterceptor) {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: UserApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            createOkHttpClient(apiInterceptor)
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UserApiDescription::class.java)

    private fun createOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
        return clientBuilder.build()
    }

    suspend fun getUser(): User {
        try {
            val response = apiDescription.getUserInfo()
            user.value = User(
                id = response.id,
                username = response.username,
                dateOfBirthday = response.dateOfBirthday,
                membershipFrom = response.membershipFrom,
                membershipTo = response.membershipTo,
                memberships = response.memberships
            )
            return user.value
        }
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Forbidden request from User service")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Unauthorized request from User service")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: BagGateWay request from User service")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("Error: from User service")
        }
        return user.value
    }

    var user = mutableStateOf(User())
}