package cz.cvut.fit.poliskyr.trainmeapp.networking

import android.content.Context
import android.content.SharedPreferences
import cz.cvut.fit.poliskyr.trainmeapp.data.source.*
import cz.cvut.fit.poliskyr.trainmeapp.util.AppSharedPreferences
import cz.cvut.fit.poliskyr.trainmeapp.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(AppSharedPreferences.SHARED_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(
        sharedPreferences: SharedPreferences
    ) = AppSharedPreferences(sharedPreferences)

    @Singleton
    @Provides
    fun provideSessionManager(
        appSharedPreferences: AppSharedPreferences
    ): SessionManager = SessionManager(
        pref = appSharedPreferences
    )

    @Singleton
    @Provides
    fun provideApiInterceptor(
        sessionManager: SessionManager
    ): ApiInterceptor = ApiInterceptor(sessionManager = sessionManager)

    @Singleton
    @Provides
    fun provideTrainerDataSource(
        apiInterceptor: ApiInterceptor
    ): TrainerDataSource = TrainerDataSource(apiInterceptor)

    @Singleton
    @Provides
    fun provideTrainingDataSource(
        apiInterceptor: ApiInterceptor
    ): TrainingDataSource = TrainingDataSource(apiInterceptor)

    @Singleton
    @Provides
    fun provideImageDataSource(
        apiInterceptor: ApiInterceptor
    ): ImageDataSource = ImageDataSource(apiInterceptor)

    @Singleton
    @Provides
    fun provideUserDataSource(
        apiInterceptor: ApiInterceptor
    ): UserDataSource = UserDataSource(apiInterceptor)

    @Singleton
    @Provides
    fun provideAuthDataSource(
        sessionManager: SessionManager
    ): AuthDataSource = AuthDataSource(sessionManager)
}