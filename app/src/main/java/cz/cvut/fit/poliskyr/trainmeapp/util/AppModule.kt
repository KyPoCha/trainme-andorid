package cz.cvut.fit.poliskyr.trainmeapp.util

import android.app.Application
import android.content.Context
import cz.cvut.fit.poliskyr.trainmeapp.networking.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }
}