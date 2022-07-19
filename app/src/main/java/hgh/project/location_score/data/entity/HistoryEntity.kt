package hgh.project.location_score.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long =0,
    val name: String,
    val score: Int
)