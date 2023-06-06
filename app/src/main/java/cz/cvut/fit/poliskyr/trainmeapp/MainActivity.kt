package cz.cvut.fit.poliskyr.trainmeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainingsRepository
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Navigation
import cz.cvut.fit.poliskyr.trainmeapp.presentation.*
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.TrainMeAppTheme
import cz.cvut.fit.poliskyr.trainmeapp.workers.data.ApiSyncWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var trainersRepository: TrainersRepository
    @Inject lateinit var trainingsRepository: TrainingsRepository
    private val trainersViewModel: TrainersViewModel by viewModels()
    private val trainingsViewModel: TrainingsViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repeatInterval = Duration.ofMinutes(5)
        val workRequest = PeriodicWorkRequestBuilder<ApiSyncWorker>(repeatInterval)
            .setInitialDelay(Duration.ofSeconds(10))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                Duration.ofSeconds(15)
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        setContent {
            setTheme(R.style.Theme_TrainMeApp)
            LaunchedEffect(Unit){
                while (true){
                    trainingsViewModel.checkOnTime()
                    delay(60000)
                }
            }

            TrainMeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        trainersViewModel = trainersViewModel,
                        trainingsViewModel = trainingsViewModel,
                        userViewModel = userViewModel,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}