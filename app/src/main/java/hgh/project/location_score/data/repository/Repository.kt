package hgh.project.location_score.data.repository

import android.app.appsearch.SearchResult
import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.response.SearchResponse

interface Repository {

    suspend fun getSearchResult(search: String, x:String, y:String): SearchResponse?

    suspend fun getHistoryList(): List<HistoryEntity>

    suspend fun addHistory(historyEntity: HistoryEntity): Long

    suspend fun deleteHistory(id: Long)

    suspend fun deleteHistoryAll()
}