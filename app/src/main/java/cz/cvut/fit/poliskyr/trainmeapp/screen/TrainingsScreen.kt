package cz.cvut.fit.poliskyr.trainmeapp.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.components.DropDownMenu
import cz.cvut.fit.poliskyr.trainmeapp.components.TopBar
import cz.cvut.fit.poliskyr.trainmeapp.components.TrainingMainColumn
import cz.cvut.fit.poliskyr.trainmeapp.components.TrainingTimeColumn
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainingsViewModel
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import cz.cvut.fit.poliskyr.trainmeapp.screen.EmptyScreen as EmptyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsScreen(navController: NavController, openDrawer: () -> Unit, trainingsViewModel: TrainingsViewModel){
    val trainers by trainingsViewModel.trainers.collectAsState()
    val trainings by trainingsViewModel.trainings.collectAsState()

    val screenState by trainingsViewModel.screenState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    var selectedTimeStartText by remember { mutableStateOf("") }
    var selectedTimeEndText by remember { mutableStateOf("") }
    val selectedTrainerUsername = remember { mutableStateOf("") }

    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth-${selectedMonth + 1}-$selectedYear"
        }, year, month, dayOfMonth
    )

    val timePickerStart = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeStartText = "$selectedHour:$selectedMinute"
        }, hour, minute, true
    )

    val timePickerEnd = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeEndText = "$selectedHour:$selectedMinute"
        }, hour, minute, true
    )

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Start: $selectedTimeStartText", color = Color.White)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "Time FAB",
                            tint = Color.White,
                        )
                    },
                    onClick = { timePickerStart.show() },
                    containerColor = PrimaryVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "End: $selectedTimeEndText", color = Color.White)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "Time FAB",
                            tint = Color.White,
                        )
                    },
                    onClick = { timePickerEnd.show() },
                    containerColor = PrimaryVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Date: $selectedDateText", color = Color.White)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = "Date FAB",
                            tint = Color.White,
                        )
                    },
                    onClick = { datePicker.show() },
                    containerColor = PrimaryVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropDownMenu(username = selectedTrainerUsername, trainers = trainers)
                Spacer(modifier = Modifier.height(8.dp))
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = selectedTrainerUsername.value, color = Color.White)
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add FAB",
                            tint = Color.White,
                        )
                    },
                    onClick = {
                              trainingsViewModel.convertTimeToApi(
                                  selectedDateText = selectedDateText,
                                  selectedTimeStartText = selectedTimeStartText,
                                  selectedTimeEndText = selectedTimeEndText,
                                  selectedTrainerUsername = selectedTrainerUsername.value,
                                  trainers = trainers,
                                  onClickSuccess = {
                                      scope.launch {
                                          trainingsViewModel.snackBarTextColor = Success
                                          snackbarHostState.showSnackbar(
                                              "Training was successfully added"
                                          )
                                          selectedDateText = ""
                                          selectedTimeStartText = ""
                                          selectedTimeEndText = ""
                                          selectedTrainerUsername.value = ""
                                      }

                                  }
                              ) {
                                  trainingsViewModel.snackBarTextColor = Danger
                                  scope.launch {
                                      snackbarHostState.showSnackbar(
                                          "Trainer with that name doesn't exist"
                                      )
                                      selectedTrainerUsername.value = ""
                                  }
                              }
                    },
                    containerColor = PrimaryVariant,
                )
            }
        },
        snackbarHost = { androidx.compose.material.SnackbarHost(snackbarHostState){snackbarData ->
                Snackbar{
                    Text(text = snackbarData.message, color = trainingsViewModel.snackBarTextColor)
                }
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            TopBar(
                title = stringResource(id = R.string.trainings),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { openDrawer() },
                isTrainingScreen = true
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TableTrainings(trainings = trainings, trainingsViewModel = trainingsViewModel, scope = scope, snackbarHostState = snackbarHostState, screenState = screenState)
            }
        }
    }
}


@Composable
fun TableTrainings(
    trainings: List<Training>,
    trainingsViewModel: TrainingsViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    screenState: Boolean
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        contentPadding = PaddingValues(8.dp,2.dp)
    ) {
        if(screenState) {
            items(trainings) { training ->
                TrainingCard(training = training) {
                    trainingsViewModel.onDelete(
                        trainingId = training.id,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
        else{
            item(){
                EmptyScreen(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun TrainingCard(training: Training, onDelete: () -> Unit){
    val dateList = training.timeFrom.split("T")
    val tmpDateList = training.timeTo.split("T")
    val date = dateList[0]
    val timeStart = dateList[1]
    val timeEnd = tmpDateList[1]
    val status = findStatus(training.timeTo, training.timeFrom)
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation =  10.dp,
            focusedElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =  Primary,
        ),
    ) {
        Column(modifier = Modifier.clickable(onClick = {  })) {
            Image(
                painter = painterResource(id = R.drawable._background1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                TrainingTimeColumn(date, timeStart, timeEnd)
                Spacer(modifier = Modifier.width(110.dp))
                TrainingMainColumn(trainerUsername = training.trainerUsername, status = status, onDelete = onDelete)
            }
        }
    }
}

@Composable
fun findStatus(timeTo: String, timeFrom: String) : String{
    val date1 = LocalDateTime.parse(timeTo)
    val date2 = LocalDateTime.parse(timeFrom)
    val now = LocalDateTime.now()
    val status: String = if(now.isAfter(date1) && date2.isAfter(now)){
        stringResource(id = R.string.in_progress)
    }
    else if(date1.isBefore(now) && date2.isBefore(now)){
        stringResource(id = R.string.finished)
    }
    else if(date1.isAfter(now) && date2.isAfter(now)){
        stringResource(id = R.string.will_be)
    }
    else{
        stringResource(id = R.string.in_progress)
    }
    return status
}