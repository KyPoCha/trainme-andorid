package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.TrainingsApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.TrainingRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object TrainingDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: TrainingsApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            OkHttpClient.Builder().addInterceptor(ApiInterceptor(AuthDataSource.getToken())).build()
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TrainingsApiDescription::class.java)

    suspend fun getTrainings() : List<Training> {
        trainings.clear()
        val response = apiDescription.getAllTrainings()
        response.forEach { trainingResponse ->
            trainings.add(
                Training(
                    trainingResponse.id,
                    trainingResponse.place,
                    trainingResponse.timeTo,
                    trainingResponse.timeFrom,
                    trainingResponse.trainerUsername
                )
            )
        }
        return trainings
    }

    suspend fun postTraining(trainingRequest: TrainingRequest) {
        val response = apiDescription.postTraining(trainingRequest)
        trainings.add(
            Training(
                response.id,
                response.place,
                response.timeTo,
                response.timeFrom,
                response.trainerUsername
            )
        )
    }

    suspend fun deleteTraining(trainingId: Int){
        val response = apiDescription.deleteTraining(trainingId)
        Log.d("DELETE: ", response.message)
    }


    var trainings = mutableListOf<Training>()

    fun setAllTrainings(mutableList: MutableList<Training>){
        this.trainings = mutableList as MutableList<Training>
    }

    fun getAllTrainings() : List<Training> = trainings
}