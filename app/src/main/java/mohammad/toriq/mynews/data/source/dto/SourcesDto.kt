package mohammad.toriq.mynews.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import mohammad.toriq.mynews.domain.model.Sources

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class SourcesDto (
    @SerializedName("sources")
    val sources :List<SourceDto>,
    @SerializedName("status")
    val status :String?,
)

fun SourcesDto.toSources(): Sources {
    return Sources(
        sources = sources.map { it.toSource() }.toList(),
        status = status,
    )
}