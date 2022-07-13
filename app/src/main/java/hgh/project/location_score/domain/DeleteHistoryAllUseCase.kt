package hgh.project.location_score.domain

import hgh.project.location_score.data.repository.Repository

internal class DeleteHistoryAllUseCase(
    private val repository: Repository
) : UseCase {

    suspend operator fun invoke() {
        return repository.deleteHistoryAll()
    }
}