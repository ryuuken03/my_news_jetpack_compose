package mohammad.toriq.mynews.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mohammad.toriq.mynews.data.source.remote.Resource
import mohammad.toriq.mynews.data.source.remote.dto.APIService
import mohammad.toriq.mynews.data.source.remote.dto.toArticles
import mohammad.toriq.mynews.data.source.remote.dto.toSources
import mohammad.toriq.mynews.domain.model.Articles
import mohammad.toriq.mynews.domain.model.Sources
import mohammad.toriq.mynews.domain.repository.Repository
import retrofit2.HttpException
import java.io.IOException

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class RepositoryImpl constructor(
    private val apiService: APIService
) : Repository
{
    override fun getSources(
        category: String?
    ): Flow<Resource<Sources>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getSourcesFromAPI(category)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getSourcesFromAPI(
        category:String?): Sources {
        val remoteSources = apiService.getSources(
            category = category,)
        return remoteSources.toSources()
    }

    override fun getSourceArticles(q : String?,
                                   sources:String,
                                   page:Int,
                                   pageSize:Int
    ):Flow<Resource<Articles>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getSourceArticlesFromAPI(q,sources,page,pageSize)))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    private suspend fun getSourceArticlesFromAPI(
        q : String?,
        sources:String,
        page: Int = 1,
        pageSize: Int = 20,): Articles {
        val remoteArticles = apiService.getSourceArticles(
            q = q,
            sources = sources,
            page = page,
            pageSize = pageSize)
        return remoteArticles.toArticles()
    }
}