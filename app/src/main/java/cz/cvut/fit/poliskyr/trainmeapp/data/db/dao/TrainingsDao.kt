package cz.cvut.fit.poliskyr.trainmeapp.data.db.dao

import androidx.room.*
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingsDao {
    @Query("SELECT * FROM trainings")
    fun getTrainings(): Flow<List<Training>>

    @Query("SELECT * FROM trainings WHERE id LIKE :id")
    fun getTraining(id : Int) : Training

    @Query("DELETE FROM trainings WHERE id = :id")
    fun deleteTrainingById(id: Int)

    @Update
    fun updateTraining(training: Training)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTraining(training: Training)

    @Query("DELETE FROM trainings")
    fun deleteAllTrainings()
}