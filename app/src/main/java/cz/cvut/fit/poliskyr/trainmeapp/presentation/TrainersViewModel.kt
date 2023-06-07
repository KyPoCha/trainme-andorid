package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.poliskyr.trainmeapp.data.db.repository.TrainersRepository
import cz.cvut.fit.poliskyr.trainmeapp.data.source.ImageDataSource
import cz.cvut.fit.poliskyr.trainmeapp.data.source.TrainerDataSource
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainersViewModel @Inject constructor(
    private val trainersRepository: TrainersRepository,
    private val trainerDataSource: TrainerDataSource,
    private val imageDataSource: ImageDataSource
    ) : ViewModel() {
    private val _trainers = MutableStateFlow(listOf<Trainer>())
    val trainers: StateFlow<List<Trainer>> = _trainers

    init {
        viewModelScope.launch {
            _trainers.value = trainerDataSource.getTrainers()
            _trainers.value.forEach{
                trainersRepository.insertTrainer(it)
                loadImageToTrainer(it.id)
            }
        }
    }

    fun loadImageToTrainer(trainerId: Int){
        viewModelScope.launch {
           val image = imageDataSource.getImageForTrainer(trainerId)
            val trainer = _trainers.value.find { it.id == trainerId }
            if(trainer != null){
                trainer.image = image.imageBytes
                trainersRepository.updateTrainer(trainer)
            }
        }
    }

    fun decodeImageBytes(imageBytes: String): Bitmap {
        val decodedBytes: ByteArray = android.util.Base64.decode(imageBytes, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}

