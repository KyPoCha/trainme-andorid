package cz.cvut.fit.poliskyr.trainmeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cz.cvut.fit.poliskyr.trainmeapp.data.db.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Navigation
import cz.cvut.fit.poliskyr.trainmeapp.presentation.AuthViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.MainViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainersViewModel
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainingsViewModel
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.TrainMeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var trainersRepository: TrainersRepository
    private val trainersViewModel = TrainersViewModel()
    private val trainingsViewModel = TrainingsViewModel()
    private val mainViewModel = MainViewModel()
    private val authViewModel = AuthViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trainersViewModel.trainersRepository = trainersRepository
        trainingsViewModel.trainersRepository = trainersRepository
        setContent {
            setTheme(R.style.Theme_TrainMeApp)
            TrainMeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        trainersViewModel = trainersViewModel,
                        trainingsViewModel = trainingsViewModel,
                        mainViewModel = mainViewModel,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}