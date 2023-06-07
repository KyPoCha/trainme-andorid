package cz.cvut.fit.poliskyr.trainmeapp.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fit.poliskyr.trainmeapp.components.TopBar
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.components.TrainerCardInfo
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Screen
import cz.cvut.fit.poliskyr.trainmeapp.presentation.TrainersViewModel
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.*

@Composable
fun TrainersScreen(navController: NavController, trainersViewModel: TrainersViewModel, openDrawer: () -> Unit){
    val trainers by trainersViewModel.trainers.collectAsState()
    trainers.forEach { trainersViewModel.loadImageToTrainer(trainerId = it.id) }
    Log.d("API CALL: ", trainers.toString())
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.trainers),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { openDrawer() },
            isTrainingScreen = false
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TrainersGrid(trainers = trainers, trainersViewModel = trainersViewModel) { navController.navigate(Screen.TrainingsScreen.route) }
        }
    }
}

@Composable
fun TrainersGrid(trainers: List<Trainer>,trainersViewModel: TrainersViewModel, onClick: () -> Unit, ) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        contentPadding = PaddingValues(8.dp,2.dp)
    ) {
        items(trainers) { trainer->
            TrainerGridItem(trainer = trainer, image = trainersViewModel.decodeImageBytes(trainer.image!!).asImageBitmap() ,onClick = onClick)
        }
    }
}


@Composable
fun TrainerGridItem(
    trainer: Trainer,
    onClick: () -> Unit,
    image: ImageBitmap
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation =  10.dp,
            focusedElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor =  InfoLight,
        ),
    ) {
        Column(modifier = Modifier.clickable(onClick = {  })) {
            trainer.image?.let {
                Image(
                    bitmap = image,
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            TrainerCardInfo(trainer = trainer, onClick = onClick)
        }
    }
}