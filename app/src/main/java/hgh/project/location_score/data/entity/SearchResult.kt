package hgh.project.location_score.data.entity

import java.io.Serializable

data class SearchResult(
    val resultList: List<String>,
    val score : Int
): Serializable
