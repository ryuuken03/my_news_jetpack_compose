package mohammad.toriq.mynews.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import mohammad.toriq.mynews.domain.model.Articles

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class ArticlesDto (
    @SerializedName("articles")
    val articles :List<ArticleDto>,
    @SerializedName("status")
    val status :String?,
    @SerializedName("totalResults")
    val totalResults :Int = 0,
)

fun ArticlesDto.toArticles(): Articles {
    return Articles(
        articles = articles.map { it.toArticle() }.toList(),
        status = status,
        totalResults = totalResults,
    )
}