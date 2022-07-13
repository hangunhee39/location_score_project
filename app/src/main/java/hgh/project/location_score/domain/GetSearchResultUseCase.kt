package hgh.project.location_score.domain

import hgh.project.location_score.data.repository.Repository
import hgh.project.location_score.data.response.SearchResponse

internal class GetSearchResultUseCase(
    private val repository : Repository
): UseCase {

    suspend operator fun invoke(search: String, x:String, y:String ) : SearchResponse? {
        return repository.getSearchResult(search, x, y)
    }
}