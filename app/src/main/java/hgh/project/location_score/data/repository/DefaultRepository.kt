package hgh.project.location_score.data.repository

import hgh.project.location_score.data.api.KakaoSearchKeywordService
import hgh.project.location_score.data.response.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultRepository(
    private val kaKaoApi : KakaoSearchKeywordService,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun getSearchResult(search: String, x:String, y:String): SearchResponse? = withContext(ioDispatcher) {
        val response= kaKaoApi.keywordSearch(search,x ,y)
        return@withContext if (response.isSuccessful){
            response.body()?:throw  RuntimeException("검색 실패(api: null).")
        }else{
            throw  RuntimeException("검색 실패(api 성공실패).")
        }
    }
}