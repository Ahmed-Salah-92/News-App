package com.ragdoll.newsapp

class News {
    val articles = listOf<Article>()
}

data class Article(
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
) {
    override fun toString(): String {
        return "Article: (title = '$title', url = '$url', urlToImage = '$urlToImage')"
    }
}