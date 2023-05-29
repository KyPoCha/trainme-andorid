package cz.cvut.fit.poliskyr.trainmeapp.networking.api

import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiDescription {
    @GET("image/{trainerId}")
    suspend fun getImageForTrainer(@Path("trainerId") trainerId: Int): ImageResponse
}