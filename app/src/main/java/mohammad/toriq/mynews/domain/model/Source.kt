package mohammad.toriq.mynews.domain.model

import mohammad.toriq.mynews.data.source.remote.dto.SourceDto

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class Source (
    val id :String?,
    val name :String?,
    val description :String?,
    val url :String?,
    val category :String?,
    val language :String?,
    val country :String?,
)

fun Source.toSourceDto(): SourceDto{
    return SourceDto(
        id = id,
        name = name,
        description = description,
        url = url,
        category = category,
        language = language,
        country = country,
    )
}