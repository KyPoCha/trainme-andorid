package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainingsRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainingDataSource
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.TrainingRequest
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Danger
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Success
import cz.cvut.fit.poliskyr.trainmeapp.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrainingsViewModel @Inject constructor(
    private val trainersRepository: TrainersRepository,
    private val trainingsRepository: TrainingsRepository,
    private val trainingDataSource: TrainingDataSource,
    private val appContext: Context
) : ViewModel() {
    var snackBarTextColor by mutableStateOf(Success)
    val screenState = MutableStateFlow(false)

    private val _trainers = MutableStateFlow(trainersRepository.getTrainers())
    val trainers: Flow<List<Trainer>> = _trainers.value

    private val _trainings = MutableStateFlow(listOf<Training>())
    val trainings: StateFlow<List<Training>> = _trainings

    init {
        createNotificationChannel(context = appContext)
        load()
    }

    fun load(){
        viewModelScope.launch {
            _trainers.value = trainersRepository.getTrainers()
            _trainings.value = trainingDataSource.getTrainings()
            if(_trainings.value.isNotEmpty()){
                screenState.value = true
            }
            _trainings.value.forEach{
                trainingsRepository.insertTrainings(it)
            }
        }
    }

    private fun post(trainingRequest: TrainingRequest){
        viewModelScope.launch {
            trainingDataSource.postTraining(trainingRequest)
            createTrainingAddNotification(trainingRequest)
            screenState.value = true
            load()
        }
    }

    private fun delete(trainingId: Int){
        viewModelScope.launch {
            trainingDataSource.deleteTraining(trainingId = trainingId)
            trainingsRepository.deleteTraining(trainingId = trainingId)
            load()
            if(_trainings.value.isEmpty()){
                screenState.value = false
            }
        }
    }

    fun onDelete(trainingId: Int, scope: CoroutineScope, snackbarHostState: SnackbarHostState){
        this.snackBarTextColor = Danger
        scope.launch {
            snackbarHostState.showSnackbar(
                "Training was delete"
            )
        }
        delete(trainingId)
        if(_trainings.value.isEmpty()){
            screenState.value = true
        }
        load()
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
            post(TrainingRequest(trainer.id, "404",timeFrom, timeTo))
            onClickSuccess()
        }
    }

    private fun createNotificationChannel(context: Context) {
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createTrainingAddNotification(trainingRequest: TrainingRequest) {
        val notificationId = 2
        val date = trainingRequest.timeFrom.split("T")[0]
        val time = trainingRequest.timeFrom.split("T")[1].slice(0..4)
        val builder = NotificationCompat.Builder(appContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("New training was made")
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Your new training will start at $date")
                .addLine("Starting time - $time"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createNearestTrainingNotification(diff: Long, nextTime: Date) {
        val now = LocalDateTime.now()
        val date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
        val setTimeOut = nextTime.time - date.time
        val notificationId = 3
        val builder = NotificationCompat.Builder(appContext, "CHANNEL_ID")
            .setPriority(1)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("The Nearest Training")
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Your next training")
                .addLine("Will begin in $diff minutes"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setTimeoutAfter(setTimeOut)
        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }

    fun checkOnTime() {
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val nowFormatted = now.split(" ")[0] + "T" +now.split(" ")[1]
        val nextTrainings = trainings.value.filter { it.timeFrom >= nowFormatted }
        if(nextTrainings.isNotEmpty()){
            val nextTraining = nextTrainings.reduce(Validator.Compare::min)
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date1: Date = format.parse(nowFormatted)
            val date2: Date = format.parse(nextTraining.timeFrom)
            val diff = date2.time - date1.time
            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff)
            if(diffInMinutes in 1..14){
                createNearestTrainingNotification(diffInMinutes, date2)
            }
        }
    }
}