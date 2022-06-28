package hgh.project.location_score.di

import hgh.project.location_score.data.api.buildHttpClient
import hgh.project.location_score.data.api.kakaoSearchApiService
import hgh.project.location_score.data.api.kakaoSearchRetrofit
import hgh.project.location_score.data.api.provideGsonConverterFactory
import hgh.project.location_score.data.repository.DefaultRepository
import hgh.project.location_score.data.repository.Repository
import hgh.project.location_score.domain.GetSearchResultUseCase
import hgh.project.location_score.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //ViewModel
    viewModel { MainViewModel(get()) }

    //UseCase
    factory{ GetSearchResultUseCase(get()) }

    //Repository
    single<Repository> { DefaultRepository(get(), get()) }
    single { kakaoSearchApiService(get()) }
    single { kakaoSearchRetrofit(get(),get()) }
    single { provideGsonConverterFactory() }
    single { buildHttpClient() }

}