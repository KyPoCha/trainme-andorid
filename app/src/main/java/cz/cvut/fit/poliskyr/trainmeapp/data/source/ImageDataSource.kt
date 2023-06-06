package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.Image
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.ImageApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.BadGatewayException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.ForbiddenException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.UnauthorizedException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ImageDataSource @Inject constructor(private val apiInterceptor: ApiInterceptor){
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: ImageApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
           createOkHttpClient(apiInterceptor)
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ImageApiDescription::class.java)

    private fun createOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
        return clientBuilder.build()
    }

    suspend fun getImageForTrainer(trainerId: Int) : Image{
        try {
            val response = apiDescription.getImageForTrainer(trainerId)
            return Image(response.uri, response.imageBytes, response.trainerId)
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
        return Image("response.uri, response.imageBytes, response.trainerId")
    }
}