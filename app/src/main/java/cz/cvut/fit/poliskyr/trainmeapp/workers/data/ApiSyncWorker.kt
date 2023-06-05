package cz.cvut.fit.poliskyr.trainmeapp.workers.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainerDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ApiSyncWorker @AssistedInject constructor(
    private val trainersRepository: TrainersRepository,
    private val trainerDataSource: TrainerDataSource,
    @Assisted private val context: Context,
    @Assisted private val userParameters: WorkerParameters
) : CoroutineWorker(context,userParameters){
    override suspend fun doWork(): Result {
        try {
            val response = trainerDataSource.getTrainers()
            return if (response.isNotEmpty()) {
                trainersRepository.deleteAllTrainers()
                response.forEach {
                    trainersRepository.insertTrainer(it)
                }
                Log.d("WORK MANAGER", "The app is sync!")
                Result.success()
            } else {
                Log.e("WORK MANAGER", "The app cannot to get sync!")
                Result.failure()
            }
        }
        catch (e: Exception){
            Log.e("WORK MANAGER", e.message!!)
            Result.retry()
        }
        return Result.success()
    }
}