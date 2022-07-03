package hgh.project.location_score.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResult(
    val resultList: List<String>,
    val score : Int
): Parcelable
