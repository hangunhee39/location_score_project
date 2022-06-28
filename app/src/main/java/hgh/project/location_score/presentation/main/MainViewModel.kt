package hgh.project.location_score.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.domain.GetSearchResultUseCase
import hgh.project.location_score.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel(
    val getSearchResultUseCase: GetSearchResultUseCase
) : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
        setState(MainState.Uninitialized)
    }

    private var _mainStateLiveData = MutableLiveData<MainState>()
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    private fun setState(state: MainState) {
        _mainStateLiveData.postValue(state)
    }

    fun searchResult(x: String, y:String) = viewModelScope.launch {
        setState(MainState.Loading)
        val searchResult =getSearchResultUseCase("편의점", x, y)
        val score = searchResult?.meta?.totalCount
        val searchList : MutableList<String> = arrayListOf()
        searchResult?.documents?.forEach {
           searchList.add(it.placeName ?: "error")
        }

        setState(MainState.Success(SearchResult(
            resultList = searchList,
            score = score?:0
        )))
    }
}