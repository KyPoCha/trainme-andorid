package cz.cvut.fit.poliskyr.trainmeapp.data.db.repository

import cz.cvut.fit.poliskyr.trainmeapp.data.db.dao.TrainingsDao
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingsRepository @Inject constructor(
    private val dao: TrainingsDao
) {
    fun getTrainings(): Flow<List<Training>> = dao.getTrainings()

    fun updateTrainings(training: Training) = dao.updateTraining(training)

    fun deleteTraining(trainingId: Int) = dao.deleteTrainingById(trainingId)

    fun insertTrainings(training: Training) = dao.insertTraining(training)
}