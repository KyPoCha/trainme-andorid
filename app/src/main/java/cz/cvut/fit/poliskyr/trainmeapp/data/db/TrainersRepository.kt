package cz.cvut.fit.poliskyr.trainmeapp.data.db

import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainersRepository @Inject constructor(
    private val dao: TrainersDao
) {
    fun getTrainers(): Flow<List<Trainer>> = dao.getTrainers()

    fun updateTrainer(trainer: Trainer) = dao.updateTrainer(trainer)

    fun insertTrainer(trainer: Trainer) = dao.insertTrainer(trainer)
}