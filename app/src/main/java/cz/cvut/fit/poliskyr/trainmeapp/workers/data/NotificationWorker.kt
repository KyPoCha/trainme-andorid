package cz.cvut.fit.poliskyr.trainmeapp.workers.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val userParameters: WorkerParameters
) : Worker(context, userParameters) {

    override fun doWork(): Result {
        return try {
            for (i in 0..10) {
                Log.i("NotificationWorker", "Counted $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}