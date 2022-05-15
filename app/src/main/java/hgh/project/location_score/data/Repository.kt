package hgh.project.location_score.data

import hgh.project.location_score.BuildConfig
import hgh.project.location_score.data.service.KakaoSearchKeywordService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {

    private val kakaoSearchApiService: KakaoSearchKeywordService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private fun buildHttpClient():OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    }else{
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}