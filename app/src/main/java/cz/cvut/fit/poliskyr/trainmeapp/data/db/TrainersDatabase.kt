package cz.cvut.fit.poliskyr.trainmeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer

@Database(entities = [Trainer::class], version = 1, exportSchema = false)
abstract class TrainersDatabase : RoomDatabase() {
    abstract fun trainersDao(): TrainersDao
}