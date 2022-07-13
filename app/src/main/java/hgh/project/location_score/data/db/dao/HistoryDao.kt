package hgh.project.location_score.data.db.dao

import androidx.room.*
import hgh.project.location_score.data.entity.HistoryEntity

@Dao
interface HistoryDao{

    @Query("SELECT * FROM HistoryEntity")
    suspend fun getAll(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity): Long

    @Query("DELETE FROM HistoryEntity WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(historyEntity: HistoryEntity)
}