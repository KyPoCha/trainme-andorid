package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.db.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.ImageDataSource
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainerDataSource
import cz.cvut.fit.poliskyr.trainmeapp.model.Image
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainersViewModel : ViewModel() {
    private val _trainers = MutableStateFlow(listOf<Trainer>())
    val trainers: StateFlow<List<Trainer>> = _trainers

    lateinit var trainersRepository: TrainersRepository

    init {
        viewModelScope.launch {
            _trainers.value = TrainerDataSource.getTrainers()
            _trainers.value.forEach{
                trainersRepository.insertTrainer(it)
            }
        }
    }

    fun loadImageToTrainer(trainerId: Int){
        viewModelScope.launch {
           val image = ImageDataSource.getImageForTrainer(trainerId)
            val trainer = _trainers.value.find { it.id == trainerId }
            if(trainer != null){
                trainer.image = decodeImageBytes(image.imageBytes)
                trainersRepository.updateTrainer(trainer)
            }
        }
    }

    private fun decodeImageBytes(imageBytes: String): Bitmap {
        val decodedBytes: ByteArray = android.util.Base64.decode(imageBytes, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}

