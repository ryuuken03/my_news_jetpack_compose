package mohammad.toriq.mynews.domain.model

import mohammad.toriq.mynews.data.source.remote.dto.ArticlesDto

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class Articles (
    val articles :List<Article>,
    val status :String?,
    val totalResults :Int = 0,
)

fun Articles.toArticlesDto(): ArticlesDto {
    return ArticlesDto(
        articles = articles.map { it.toArticleDto() }.toList(),
        status = status,
        totalResults = totalResults,
    )
}