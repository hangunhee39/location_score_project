package hgh.project.location_score.di

import hgh.project.location_score.data.api.buildHttpClient
import hgh.project.location_score.data.api.kakaoSearchApiService
import hgh.project.location_score.data.api.kakaoSearchRetrofit
import hgh.project.location_score.data.api.provideGsonConverterFactory
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    //Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    single { kakaoSearchApiService(get()) }
    single { kakaoSearchRetrofit(get(),get()) }
    single { provideGsonConverterFactory() }
    single { buildHttpClient() }
}