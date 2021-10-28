package com.tinyapps.todoapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import com.tinyapps.todoapp.data.TaskDatabase
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

//import dagger.hilt.android.components.ApplicationComponent

@Module
//@InstallIn(ApplicationComponent::class)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TaskDatabase.Callback
    ) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope()= CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope