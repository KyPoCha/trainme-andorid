package cz.cvut.fit.poliskyr.trainmeapp.data.source

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import cz.cvut.fit.poliskyr.trainmeapp.networking.api.TrainingsApiDescription
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.BadGatewayException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.ForbiddenException
import cz.cvut.fit.poliskyr.trainmeapp.networking.exception.UnauthorizedException
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.TrainingRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class TrainingDataSource @Inject constructor(private val apiInterceptor: ApiInterceptor) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val apiDescription: TrainingsApiDescription = Retrofit.Builder()
        .baseUrl(LOCAL_HOST)
        .client(
            createOkHttpClient(apiInterceptor)
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(TrainingsApiDescription::class.java)

    private fun createOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
        return clientBuilder.build()
    }

    suspend fun getTrainings() : List<Training> {
        try {
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
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Forbidden request from Training service")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Unauthorized request from Training service")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: BadGateWay request from Training service")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("Error: from Training service")
        }
        return trainings
    }

    suspend fun postTraining(trainingRequest: TrainingRequest) {
        try {
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
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Forbidden request from Training service")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Unauthorized request from Training service")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: BadGateWay request from Training service")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("Error: from Training service")
        }
    }

    suspend fun deleteTraining(trainingId: Int){
        try {
            val response = apiDescription.deleteTraining(trainingId)
            Log.d("DELETE: ", response.message)
        }
        catch (e: ForbiddenException) {
            Log.e("API", "Forbidden")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Forbidden request from Training service")
        } catch (e: UnauthorizedException) {
            Log.e("API", "Unauthorized")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: Unauthorized request from Training service")
        } catch (e: BadGatewayException) {
            Log.e("API", "BadGateway")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("API: BadGateWay request from Training service")
        } catch (e: Exception) {
            Log.e("API", "ERROR")
            Firebase.crashlytics.recordException(e)
            FirebaseCrashlytics.getInstance().log("Error: from Training service")
        }
    }


    var trainings = mutableListOf<Training>()
}