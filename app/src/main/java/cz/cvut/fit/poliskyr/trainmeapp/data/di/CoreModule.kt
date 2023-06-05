package cz.cvut.fit.poliskyr.trainmeapp.data.di

import android.content.Context
import androidx.room.Room
import cz.cvut.fit.poliskyr.trainmeapp.data.db.AppDatabase
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.model.Training
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
        AppDatabase::class.java,
        "trainer_database"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTrainersDao(db: AppDatabase) = db.trainersDao()

    @Provides
    @Singleton
    fun provideTrainingsDao(db: AppDatabase) = db.trainingsDao()

    @Provides
    fun provideTrainerEntity() = Trainer()

    @Provides
    fun provideTrainingEntity() = Training()
}