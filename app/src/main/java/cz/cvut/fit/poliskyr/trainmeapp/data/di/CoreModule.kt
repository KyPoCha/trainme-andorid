package cz.cvut.fit.poliskyr.trainmeapp.data.di

import android.content.Context
import androidx.room.Room
import cz.cvut.fit.poliskyr.trainmeapp.data.db.TrainersDatabase
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun resolve(@ApplicationContext context: Context) = Room.databaseBuilder (
        context,
        TrainersDatabase::class.java,
        "trainer_database"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun resolveDao(db: TrainersDatabase) = db.trainersDao()

    @Provides
    fun resolveEntity() = Trainer()
}