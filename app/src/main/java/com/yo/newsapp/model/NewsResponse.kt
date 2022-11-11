package com.yo.newsapp.model

import com.google.gson.annotations.SerializedName
import com.yo.newsapp.model.Article

data class NewsResponse (
    @SerializedName("articles")
    var articles: MutableList<Article>,
    @SerializedName("Status")
    var status: String,
    @SerializedName("totalResults")
    var totalResult: Int?
)