package hgh.project.location_score.di

import hgh.project.location_score.data.api.buildHttpClient
import hgh.project.location_score.data.api.kakaoSearchApiService
import hgh.project.location_score.data.api.kakaoSearchRetrofit
import hgh.project.location_score.data.api.provideGsonConverterFactory
import hgh.project.location_score.data.db.provideDB
import hgh.project.location_score.data.db.provideDao
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.data.repository.DefaultRepository
import hgh.project.location_score.data.repository.Repository
import hgh.project.location_score.domain.*
import hgh.project.location_score.domain.AddHistoryUseCase
import hgh.project.location_score.domain.DeleteHistoryAllUseCase
import hgh.project.location_score.domain.DeleteHistoryUseCase
import hgh.project.location_score.domain.GetHistoryListUseCase
import hgh.project.location_score.domain.GetSearchResultUseCase
import hgh.project.location_score.presentation.main.MainViewModel
import hgh.project.location_score.presentation.result.ResultViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //ViewModel
    viewModel { MainViewModel(get(),get(),get()) }
    viewModel { (result : SearchResult) -> ResultViewModel(result , get())}

    //UseCase
    factory{ GetSearchResultUseCase(get()) }
    factory { GetHistoryListUseCase(get()) }
    factory { AddHistoryUseCase(get()) }
    factory { DeleteHistoryAllUseCase(get()) }
    factory { DeleteHistoryUseCase(get()) }

    //Repository
    single<Repository> { DefaultRepository(get(), get(), get()) }
    single { kakaoSearchApiService(get()) }
    single { kakaoSearchRetrofit(get(),get()) }
    single { provideGsonConverterFactory() }
    single { buildHttpClient() }

    //DB
    single { provideDB(androidApplication())}
    single { provideDao(get())}

}