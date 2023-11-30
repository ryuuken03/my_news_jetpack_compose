package mohammad.toriq.mynews.domain.usecase

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mynews.data.source.remote.Resource
import mohammad.toriq.mynews.domain.model.Articles
import mohammad.toriq.mynews.domain.repository.Repository

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class GetArticlesUseCase(
    private val repository: Repository
) {
    operator fun invoke(q : String?,
                        sources:String,
                        page: Int = 1,
//                        pageSize: Int = 20
                        pageSize: Int = 5
    ): Flow<Resource<Articles>> {
        return repository.getSourceArticles(q,sources,page,pageSize)
    }
}