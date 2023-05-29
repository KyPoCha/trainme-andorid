package cz.cvut.fit.poliskyr.trainmeapp.data.source

import androidx.compose.runtime.mutableStateOf
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.User
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.TrainingsApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.UserApiDescription
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object UserDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: UserApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            OkHttpClient.Builder().addInterceptor(ApiInterceptor(AuthDataSource.getToken())).build()
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(UserApiDescription::class.java)

    suspend fun getUser(): User{
        val response = apiDescription.getUserInfo()
        user.value = User(
            response.id,
            response.username,
            response.dateOfBirthday,
            response.membershipFrom,
            response.membershipTo,
            response.memberships
        )
        return user.value
    }

    var user = mutableStateOf(User())

    fun getUserDate() = user.value

}