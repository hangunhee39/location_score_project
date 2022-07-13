package hgh.project.location_score.domain

import hgh.project.location_score.data.repository.Repository

internal class DeleteHistoryUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke(id: Long) {
        return repository.deleteHistory(id)
    }
}