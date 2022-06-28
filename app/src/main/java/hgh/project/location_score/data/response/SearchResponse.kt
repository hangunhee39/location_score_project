package hgh.project.location_score.data.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("documents")
    val documents: List<Document>?,
    @SerializedName("meta")
    val meta: Meta?
)