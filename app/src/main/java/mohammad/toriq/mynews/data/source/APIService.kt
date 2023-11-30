package mohammad.toriq.mynews.data.source.remote.dto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
interface APIService {
    companion object {
        const val BASE_URL: String = "https://newsapi.org/v2/"
    }

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query(value = "category", encoded = true) category: String?,
    ): SourcesDto

    @GET("everything")
    suspend fun getSourceArticles(
        @Query(value = "sources", encoded = true) sources: String? = null,
        @Query(value = "q", encoded = true) q: String? = null,
        @Query(value = "page", encoded = true) page: Int,
        @Query(value = "pageSize", encoded = true) pageSize: Int,
    ): ArticlesDto
}