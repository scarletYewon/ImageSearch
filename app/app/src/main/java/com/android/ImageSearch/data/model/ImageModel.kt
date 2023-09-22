package com.android.ImageSearch.data.model

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Document
import java.util.Date

data class ImageModel(
    @SerializedName("documents")
    val documents:ArrayList<Document>,

    @SerializedName("meta")
    val meta: Meta
) {
    data class Document (
        @SerializedName("collection")
        val collection: String,
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("width")
        val width: Int,
        @SerializedName("height")
        val height: Int,
        @SerializedName("display_sitename")
        val displaySitename: String,
        @SerializedName("doc_url")
        val docUrl: String,
        @SerializedName("datetime")
        val datetime: String,

    )

    data class Meta (
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("total_count")
        val totalCount: Int,
    )
}
