package cz.cvut.fit.poliskyr.trainmeapp.presentation

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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

    private val _trainerImages = mutableMapOf<Int, ImageBitmap>()
    val trainerImages: Map<Int, ImageBitmap> = _trainerImages

    init {
        load()
        trainers.value.forEach { loadImageToTrainer(it.id) }
    }

    fun load(){
        viewModelScope.launch {
            _trainers.value = trainerDataSource.getTrainers().toList()
            _trainers.value.forEach{
                loadImageToTrainer(it.id)
                trainersRepository.insertTrainer(it)
            }
        }
    }

    fun loadImageToTrainer(trainerId: Int){
        viewModelScope.launch {
            val image = imageDataSource.getImageForTrainer(trainerId)
            val decodedImage = decodeImageBytes(image.imageBytes)
            _trainerImages[trainerId] = decodedImage
            val updatedTrainers = _trainers.value.map { trainer ->
                if (trainer.id == trainerId) {
                    trainer.copy(image = image.imageBytes)
                } else {
                    trainer
                }
            }
            _trainers.value = updatedTrainers
            _trainers.value.forEach { trainersRepository.updateTrainer(it) }
        }
    }

    fun decodeImageBytes(imageBytes: String): ImageBitmap {
        val decodedBytes: ByteArray = android.util.Base64.decode(imageBytes, android.util.Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size).asImageBitmap()
    }
}

