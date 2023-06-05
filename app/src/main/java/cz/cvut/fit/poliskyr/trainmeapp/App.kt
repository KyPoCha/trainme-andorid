package cz.cvut.fit.poliskyr.trainmeapp

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import cz.cvut.fit.poliskyr.trainmeapp.workers.factory.ApiSyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: ApiSyncWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
}