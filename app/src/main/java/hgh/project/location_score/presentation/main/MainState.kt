package hgh.project.location_score.presentation.main

import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.entity.SearchResult

sealed class MainState {

    object Uninitialized: MainState()

    object Loading: MainState()

    data class Success(
        val historyList: List<HistoryEntity>
    ):MainState()

    object SearchLoading:MainState()

    data class SearchSuccess(
        val result: SearchResult
    ): MainState()

    object Error: MainState()
}