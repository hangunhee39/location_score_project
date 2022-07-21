package hgh.project.location_score.presentation.result

sealed class ResultState {

    object UnInitialized : ResultState()

    data class Success(
      val resultList: List<String> = listOf(),
      val score: Int
    ): ResultState()

    object Error : ResultState()
}