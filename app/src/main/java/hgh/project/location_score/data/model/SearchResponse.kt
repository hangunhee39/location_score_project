package hgh.project.location_score.data.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("documents")
    val documents: List<Any>?,
    @SerializedName("meta")
    val meta: Meta?
)