package hgh.project.location_score.presentation.main

import androidx.lifecycle.viewModelScope
import hgh.project.location_score.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {}

}