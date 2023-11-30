package mohammad.toriq.mynews.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import mohammad.toriq.mynews.data.source.remote.dto.SourceDto
import mohammad.toriq.mynews.domain.model.Article

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class ArticleDto (
    @SerializedName("source")
    val source :SourceDto,
    @SerializedName("author")
    val author :String?,
    @SerializedName("title")
    val title :String?,
    @SerializedName("description")
    val description :String?,
    @SerializedName("url")
    val url :String?,
    @SerializedName("urlToImage")
    val urlToImage :String?,
    @SerializedName("publishedAt")
    val publishedAt :String?,
    @SerializedName("content")
    val content :String?,
)

fun ArticleDto.toArticle(): Article {
    return Article(
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}