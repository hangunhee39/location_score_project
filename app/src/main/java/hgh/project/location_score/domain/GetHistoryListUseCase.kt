package hgh.project.location_score.domain

import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.data.repository.Repository

internal class GetHistoryListUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(): List<HistoryEntity> {
        return repository.getHistoryList()
    }
}