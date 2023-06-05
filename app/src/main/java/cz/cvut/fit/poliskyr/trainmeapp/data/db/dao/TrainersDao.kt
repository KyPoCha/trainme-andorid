package cz.cvut.fit.poliskyr.trainmeapp.data.db.dao

import androidx.room.*
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainersDao {
    @Query("SELECT * FROM trainers")
    fun getTrainers(): Flow<List<Trainer>>

    @Query("SELECT * FROM trainers WHERE id LIKE :id")
    fun getTrainer(id : Int) : Trainer

    @Update
    fun updateTrainer(trainerEntity: Trainer)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrainer(trainerEntity: Trainer)

    @Query("DELETE FROM trainers")
    fun deleteAllTrainers()
}