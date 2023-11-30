package mohammad.toriq.mynews.domain.model

import mohammad.toriq.mynews.data.source.remote.dto.SourcesDto

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class Sources (
    val sources :List<Source>,
    val status :String?,
)

fun Sources.toSourcesDto(): SourcesDto {
    return SourcesDto(
        sources = sources.map { it.toSourceDto() }.toList(),
        status = status,
    )
}