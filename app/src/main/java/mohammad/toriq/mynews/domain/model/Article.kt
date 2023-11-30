package mohammad.toriq.mynews.domain.model

import mohammad.toriq.mynews.data.source.remote.dto.ArticleDto

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class Article (
    val source :Source,
    val author :String?,
    val title :String?,
    val description :String?,
    val url :String?,
    val urlToImage :String?,
    val publishedAt :String?,
    val content :String?,
)

fun Article.toArticleDto(): ArticleDto {
    return ArticleDto(
        source = source.toSourceDto(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}