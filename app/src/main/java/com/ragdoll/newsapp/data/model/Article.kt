package com.ragdoll.newsapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
) : Serializable {
    /*
    Issue:
        The crash is not directly caused by the WebView navigation.
        It happens because the Article object passed between fragments
        contains a null field (likely url, title, or author),
        and your Article class's hashCode() method does not handle null values.
    Explanation:
        This prevents a NullPointerException when navigating between fragments,
        even if some fields are null.
    */
    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        // Add other fields similarly
        return result
    }
    /** Explanation of hashCode() implementation:
    The numbers 17 and 31 are commonly used in hash code implementations because:
     * 17: Used as an initial non-zero value to reduce the chance of hash collisions.
     * 31: A small, odd prime number that helps distribute hash codes uniformly.
    Multiplying by 31 is efficient (can be optimized as (result << 5) - result).
    This pattern is recommended by Effective Java for generating good hash codes.
     * */

}