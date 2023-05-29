package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.util.Log
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.db.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainerDataSource
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainingDataSource
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.TrainingRequest
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Danger
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainingsViewModel : ViewModel() {
    var snackBarTextColor by mutableStateOf(Success)
    val screenState = MutableStateFlow(true)

    private val _trainers = MutableStateFlow(listOf<Trainer>())
    val trainers: StateFlow<List<Trainer>> = _trainers

    private val _trainings = MutableStateFlow(listOf<Training>())
    val trainings: StateFlow<List<Training>> = _trainings

    lateinit var trainersRepository: TrainersRepository

    init {
        viewModelScope.launch {
            screenState.value = false
            _trainers.value = TrainerDataSource.getTrainers()
            _trainings.value = TrainingDataSource.getTrainings()
        }
    }

    private fun post(trainingRequest: TrainingRequest){
        viewModelScope.launch {
            TrainingDataSource.postTraining(trainingRequest)
            screenState.value = true
            _trainings.value = TrainingDataSource.getTrainings()
        }
    }

    private fun delete(trainingId: Int){
        viewModelScope.launch {
            TrainingDataSource.deleteTraining(trainingId = trainingId)
            _trainings.value = TrainingDataSource.getTrainings()
        }
    }

    fun onDelete(trainingId: Int, scope: CoroutineScope, snackbarHostState: SnackbarHostState){
        this.snackBarTextColor = Danger
        scope.launch {
            snackbarHostState.showSnackbar(
                "Training was delete"
            )
        }
        this.delete(trainingId)
        if(_trainings.value.isEmpty()){
            screenState.value = true
        }
    }

    fun convertTimeToApi(
        selectedDateText: String,
        selectedTimeStartText: String,
        selectedTimeEndText: String,
        selectedTrainerUsername: String,
        trainers: List<Trainer>,
        onClickSuccess: () -> Job,
        onClickFailure: () -> Job
    ){
        val splitedArrayDate = selectedDateText.split("-")
        val yearDate = splitedArrayDate[2]
        val dayDate = if(splitedArrayDate[0].toInt() < 10){
            "0" + splitedArrayDate[0]
        } else {
            splitedArrayDate[0]
        }
        val monthDate = if(splitedArrayDate[1].toInt() < 10){
            "0" + splitedArrayDate[1]
        } else {
            splitedArrayDate[1]
        }
        val splitedTimeEndText = selectedTimeEndText.split(":")
        val hourEndDate = if(splitedTimeEndText[0].toInt() < 10){
            "0" + splitedTimeEndText[0]
        }
        else{
            splitedTimeEndText[0]
        }
        val minuteEndDate = if(splitedTimeEndText[1].toInt() < 10){
            "0" + splitedTimeEndText[1]
        }
        else{
            splitedTimeEndText[1]
        }
        val splitedTimeStartText = selectedTimeStartText.split(":")
        val hourStartDate = if(splitedTimeStartText[0].toInt() < 10){
            "0" + splitedTimeStartText[0]
        }
        else{
            splitedTimeStartText[0]
        }
        val minuteStartDate = if(splitedTimeStartText[1].toInt() < 10){
            "0" + splitedTimeStartText[1]
        }
        else{
            splitedTimeStartText[1]
        }
        val timeTo = yearDate + "-" + monthDate + "-" + dayDate + "T" + hourEndDate + ":" + minuteEndDate + ":00"
        val timeFrom = yearDate + "-" + monthDate + "-" + dayDate + "T" + hourStartDate + ":" + minuteStartDate + ":00"
        val trainer = trainers.find { it.username == selectedTrainerUsername }
        if(trainer == null) {
            onClickFailure()
        }
        else {
            this.post(TrainingRequest(trainer.id, "404",timeFrom, timeTo))
            onClickSuccess()
        }
    }
}