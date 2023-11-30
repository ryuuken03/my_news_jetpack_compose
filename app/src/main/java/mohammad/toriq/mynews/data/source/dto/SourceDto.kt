package mohammad.toriq.mynews.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import mohammad.toriq.mynews.domain.model.Source

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class SourceDto (
    @SerializedName("id")
    val id :String?,
    @SerializedName("name")
    val name :String?,
    @SerializedName("description")
    val description :String?,
    @SerializedName("url")
    val url :String?,
    @SerializedName("category")
    val category :String?,
    @SerializedName("language")
    val language :String?,
    @SerializedName("country")
    val country :String?,
)

fun SourceDto.toSource(): Source{
    return Source(
        id = id,
        name = name,
        description = description,
        url = url,
        category = category,
        language = language,
        country = country,
    )
}