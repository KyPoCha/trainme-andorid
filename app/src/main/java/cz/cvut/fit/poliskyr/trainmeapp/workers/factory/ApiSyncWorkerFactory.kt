package cz.cvut.fit.poliskyr.trainmeapp.workers.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainerDataSource
import cz.cvut.fit.poliskyr.trainmeapp.workers.data.ApiSyncWorker
import javax.inject.Inject

class ApiSyncWorkerFactory @Inject constructor(
    private val trainersRepository: TrainersRepository,
    private val trainerDataSource: TrainerDataSource
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ApiSyncWorker(trainersRepository, trainerDataSource, appContext, workerParameters)
}