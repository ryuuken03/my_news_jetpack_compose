package mohammad.toriq.mynews.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mynews.data.source.remote.Resource
import mohammad.toriq.mynews.domain.model.Sources
import mohammad.toriq.mynews.domain.repository.Repository

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class GetSourcesUseCase(
    private val repository: Repository
) {
    operator fun invoke(category:String?
    ): Flow<Resource<Sources>> {
        return repository.getSources(category)
    }
}