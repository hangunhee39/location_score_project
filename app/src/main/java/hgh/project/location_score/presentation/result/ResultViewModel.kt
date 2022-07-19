package hgh.project.location_score.presentation.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.domain.AddHistoryUseCase
import hgh.project.location_score.presentation.BaseViewModel
import hgh.project.location_score.presentation.main.MainState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ResultViewModel(
    private val result : SearchResult?,
    val addHistoryUseCase: AddHistoryUseCase
): BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ResultState.Loading)
        result?.let {
            setState(
                ResultState.Success(it.resultList,it.score)
            )
        } ?: kotlin.run {
            setState(
                ResultState.Error
            )
        }
    }

    private var _resultStateLiveData = MutableLiveData<ResultState>(ResultState.UnInitialized)
    val resultStateLiveData: LiveData<ResultState> = _resultStateLiveData

    private fun setState(state: ResultState) {
        _resultStateLiveData.postValue(state)
    }

    fun addHistory(history: HistoryEntity)=viewModelScope.launch{
        addHistoryUseCase(history)
    }
}