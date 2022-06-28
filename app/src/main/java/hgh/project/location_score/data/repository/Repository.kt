package hgh.project.location_score.data.repository

import android.app.appsearch.SearchResult
import hgh.project.location_score.data.response.SearchResponse

interface Repository {

    suspend fun getSearchResult(search: String, x:String, y:String): SearchResponse?
}