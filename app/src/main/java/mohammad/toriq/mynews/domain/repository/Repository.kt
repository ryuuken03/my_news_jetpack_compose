package mohammad.toriq.mynews.domain.repository

import kotlinx.coroutines.flow.Flow
import mohammad.toriq.mynews.data.source.remote.Resource
import mohammad.toriq.mynews.domain.model.Articles
import mohammad.toriq.mynews.domain.model.Sources

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
interface Repository {
    fun getSources(category:String?
    ): Flow<Resource<Sources>>

    fun getSourceArticles(q :String?,
                          sources:String,
                          page: Int = 1,
                          pageSize: Int = 20
    ): Flow<Resource<Articles>>
}