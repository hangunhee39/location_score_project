package hgh.project.location_score.data.service

import hgh.project.location_score.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoSearchKeywordService {

    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("v2/local/search/keyword.json?radius=1000")
    suspend fun keywordSearch(
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String
    )
}