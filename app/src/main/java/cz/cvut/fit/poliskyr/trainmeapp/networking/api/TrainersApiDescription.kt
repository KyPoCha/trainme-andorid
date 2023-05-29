package cz.cvut.fit.poliskyr.trainmeapp.networking.api

import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.TrainerResponse
import retrofit2.http.GET

interface TrainersApiDescription {
    @GET("trainer/all")
    suspend fun getAllTrainers(): List<TrainerResponse>

}