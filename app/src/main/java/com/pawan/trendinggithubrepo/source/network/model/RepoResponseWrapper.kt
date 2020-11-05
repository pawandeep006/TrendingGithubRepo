package com.pawan.trendinggithubrepo.source.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepoResponseWrapper(
    @SerializedName("total_count")
    @Expose
    var totalCount: Long? = null,

    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null,

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

)