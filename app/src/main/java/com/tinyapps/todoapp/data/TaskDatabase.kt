package com.tinyapps.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tinyapps.todoapp.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Tourismusabgabe"))
                dao.insert(Task("TDA Homepage", important = true))
                dao.insert(Task("Steuerausgleich"))
                dao.insert(Task("Arztrechnungen"))
                dao.insert(Task("5th Task...", important = true, completed = true))
                dao.insert(Task("6th Task...", completed = true))

            }
        }
    }
}