package cz.cvut.fit.poliskyr.trainmeapp.data.source

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.TrainersApiDescription
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

const val LOCAL_HOST = "http://10.0.2.2:8080/api/"

object TrainerDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: TrainersApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            OkHttpClient.Builder().addInterceptor(ApiInterceptor(AuthDataSource.getToken())).build()
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TrainersApiDescription::class.java)

    suspend fun getTrainers() : List<Trainer> {
        trainers.clear()
        val response = apiDescription.getAllTrainers()
        response.forEach {
            trainerResponse ->
                trainers.add(
                    Trainer(
                        trainerResponse.id,
                        trainerResponse.username,
                        trainerResponse.reviewValue,
                        trainerResponse.email,
                        trainerResponse.telephone,
                        trainerResponse.priceForOneTraining,
                        trainerResponse.priceForMonthTraining,
                        trainerResponse.dateOfBirthday,
                        null
                    )
                )
        }
        return trainers
    }

    var trainers = mutableListOf<Trainer>()

    fun setAllTrainers(mutableList: List<Trainer>) {
        this.trainers = mutableList as MutableList<Trainer>
    }

    fun getAllTrainers(): List<Trainer> = trainers
}