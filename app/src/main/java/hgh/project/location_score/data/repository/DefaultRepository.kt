package hgh.project.location_score.data.repository

import hgh.project.location_score.data.api.KakaoSearchKeywordService
import hgh.project.location_score.data.db.dao.HistoryDao
import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.response.SearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class DefaultRepository(
    private val kaKaoApi : KakaoSearchKeywordService,
    private val ioDispatcher: CoroutineDispatcher,
    private val historyDao: HistoryDao
) : Repository {

    override suspend fun getSearchResult(search: String, x:String, y:String): SearchResponse? = withContext(ioDispatcher) {
        val response= kaKaoApi.keywordSearch(search,x ,y)
        return@withContext if (response.isSuccessful){
            response.body()?:throw  RuntimeException("검색 실패(api: null).")
        }else{
            throw  RuntimeException("검색 실패(api 성공실패).")
        }
    }

    override suspend fun getHistoryList(): List<HistoryEntity> = withContext(ioDispatcher) {
        historyDao.getAll()
    }

    override suspend fun addHistory(historyEntity: HistoryEntity): Long = withContext(ioDispatcher) {
        historyDao.insert(historyEntity)
    }

    override suspend fun deleteHistory(id: Long) = withContext(ioDispatcher) {
        historyDao.delete(id)
    }

    override suspend fun deleteHistoryAll() = withContext(ioDispatcher)  {
        historyDao.deleteAll()
    }
}