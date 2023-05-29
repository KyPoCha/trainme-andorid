package cz.cvut.fit.poliskyr.trainmeapp.data.source

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.Image
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.ImageApiDescription
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ImageDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: ImageApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            OkHttpClient.Builder().addInterceptor(ApiInterceptor(AuthDataSource.getToken())).build()
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ImageApiDescription::class.java)

    suspend fun getImageForTrainer(trainerId: Int) : Image{
        val response = apiDescription.getImageForTrainer(trainerId)
        return Image(response.uri, response.imageBytes, response.trainerId)
    }
}