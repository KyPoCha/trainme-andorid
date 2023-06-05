package cz.cvut.fit.poliskyr.trainmeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.cvut.fit.poliskyr.trainmeapp.data.db.dao.TrainersDao
import cz.cvut.fit.poliskyr.trainmeapp.data.db.dao.TrainingsDao
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.model.Training

@Database(entities = [Trainer::class, Training::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainersDao(): TrainersDao

    abstract fun trainingsDao(): TrainingsDao
}