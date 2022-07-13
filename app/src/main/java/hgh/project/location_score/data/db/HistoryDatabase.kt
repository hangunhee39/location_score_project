package hgh.project.location_score.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hgh.project.location_score.data.db.dao.HistoryDao
import hgh.project.location_score.data.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryDatabase: RoomDatabase() {

    companion object{
        const val DB_NAME ="HistoryDatabase"
    }

    abstract fun HistoryDao(): HistoryDao
}