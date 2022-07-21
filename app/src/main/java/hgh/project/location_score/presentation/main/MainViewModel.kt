package hgh.project.location_score.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.domain.DeleteHistoryUseCase
import hgh.project.location_score.domain.GetHistoryListUseCase
import hgh.project.location_score.domain.GetSearchResultUseCase
import hgh.project.location_score.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel(
    val getSearchResultUseCase: GetSearchResultUseCase,
    val getHistoryListUseCase: GetHistoryListUseCase,
    val deleteHistoryUseCase: DeleteHistoryUseCase
) : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
        setState(MainState.Loading)
        setState(MainState.Success(getHistoryListUseCase()))
    }

    private var _mainStateLiveData = MutableLiveData<MainState>(MainState.Uninitialized)
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    private fun setState(state: MainState) {
        _mainStateLiveData.postValue(state)
    }


    fun searchResult(x: String, y:String) = viewModelScope.launch {
        setState(MainState.SearchLoading)

        val searchList : MutableList<String> = arrayListOf()
        var score = 0

        //편의점
        val convenienceStoreResult =getSearchResultUseCase("편의점", x, y)
        score +=(convenienceStoreResult?.documents?.size ?:0)
        convenienceStoreResult?.documents?.forEach {
           searchList.add("(+1) \uD83C\uDF59  "+it.placeName )
        }
        //스타벅스
        val starbucksResult =getSearchResultUseCase("스타벅스", x, y)
        score +=(starbucksResult?.documents?.size ?:0)*4
        starbucksResult?.documents?.forEach {
            searchList.add("(+4) ☕ "+it.placeName )
        }
        //맥도날드
        val mDResult =getSearchResultUseCase("맥도날드", x, y)
        score +=(mDResult?.documents?.size ?:0)*3
        mDResult?.documents?.forEach {
            searchList.add("(+3) \uD83C\uDF54 "+it.placeName )
        }
        //지하철
        val subwayResult =getSearchResultUseCase("지하철", x, y)
        score +=(subwayResult?.documents?.size ?:0)*2
        subwayResult?.documents?.forEach {
            searchList.add("(+2) \uD83D\uDE8B "+it.placeName )
        }
        setState(MainState.SearchSuccess(SearchResult(
            resultList = searchList,
            score = score
        )))
    }

    fun deleteHistory(history: HistoryEntity) =viewModelScope.launch{
        deleteHistoryUseCase(history.id)
        setState(MainState.Success(getHistoryListUseCase()))
    }
}