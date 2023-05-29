package cz.cvut.fit.poliskyr.trainmeapp.networking.api

import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.TrainingRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.InfoResponse
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.response.TrainingResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TrainingsApiDescription {
    @GET("training/")
    suspend fun getAllTrainings(): List<TrainingResponse>

    @POST("training/")
    suspend fun postTraining(@Body trainingRequest: TrainingRequest): TrainingResponse

    @DELETE("training/{trainingId}")
    suspend fun deleteTraining(@Path("trainingId") trainingId: Int): InfoResponse
}