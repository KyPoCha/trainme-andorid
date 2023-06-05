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
import javax.inject.Inject

//const val LOCAL_HOST = "http://10.0.2.2:8080/api/"
const val LOCAL_HOST = "http://192.168.0.227:8080/api/"

class TrainerDataSource @Inject constructor(private val apiInterceptor: ApiInterceptor) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: TrainersApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
           createOkHttpClient(apiInterceptor)
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TrainersApiDescription::class.java)

    private fun createOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
        return clientBuilder.build()
    }

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