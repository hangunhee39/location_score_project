package hgh.project.location_score.domain

import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.repository.Repository

internal class AddHistoryUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(historyEntity: HistoryEntity): Long {
        return repository.addHistory(historyEntity)
    }
}