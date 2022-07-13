package hgh.project.location_score.data.db

import android.content.Context
import androidx.room.Room

internal fun provideDB(context: Context): HistoryDatabase =
    Room.databaseBuilder(context, HistoryDatabase::class.java, HistoryDatabase.DB_NAME).build()

internal fun provideDao(dateBase: HistoryDatabase) = dateBase.HistoryDao()