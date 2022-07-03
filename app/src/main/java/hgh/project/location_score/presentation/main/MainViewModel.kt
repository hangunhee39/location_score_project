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

    override fun fetchData(): Job = Job()

    private var _mainStateLiveData = MutableLiveData<MainState>(MainState.Uninitialized)
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    private fun setState(state: MainState) {
        _mainStateLiveData.postValue(state)
    }

    fun searchResult(x: String, y:String) = viewModelScope.launch {
        setState(MainState.Loading)

        val searchList : MutableList<String> = arrayListOf()
        var score = 0;

        //편의점
        val convenienceStoreResult =getSearchResultUseCase("편의점", x, y)
        score +=(convenienceStoreResult?.documents?.size ?:0)
        convenienceStoreResult?.documents?.forEach {
           searchList.add(it.placeName ?: "error")
        }
        //스타벅스
        val starbucksResult =getSearchResultUseCase("스타벅스", x, y)
        score +=(starbucksResult?.documents?.size ?:0)
        starbucksResult?.documents?.forEach {
            searchList.add(it.placeName ?: "error")
        }
        //맥도날드
        val mDResult =getSearchResultUseCase("맥도날드", x, y)
        score +=(mDResult?.documents?.size ?:0)
        mDResult?.documents?.forEach {
            searchList.add(it.placeName ?: "error")
        }
        //맥도날드
        val subwayResult =getSearchResultUseCase("지하철", x, y)
        score +=(subwayResult?.documents?.size ?:0)
        subwayResult?.documents?.forEach {
            searchList.add(it.placeName ?: "error")
        }
        setState(MainState.Success(SearchResult(
            resultList = searchList,
            score = score
        )))
    }
}