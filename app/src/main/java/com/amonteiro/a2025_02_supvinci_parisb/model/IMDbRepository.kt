package com.amonteiro.a2025_02_supvinci_parisb.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request

data class IMDbResponse(
    @SerializedName("results")
    val results: List<MovieBean>
)

data class MovieBean(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("primaryTitle")
    val title: String = "Titre inconnu",
    @SerializedName("primaryImage")
    val image: String? = null,
    @SerializedName("description")
    val description: String? = null
)

object IMDbRepository {

    private val client = OkHttpClient()
    private const val API_KEY = "b9d6898d0dmsh1706e8dce7fe4aap1104e6jsn041369779522"
    private const val API_HOST = "imdb236.p.rapidapi.com"

    fun searchMovies(query: String): List<MovieBean> {
        val url = "https://imdb236.p.rapidapi.com/imdb/search" +
                "?type=movie" +
                "&primaryTitle=$query" +
                "&rows=25" +
                "&startYearFrom=37" +
                "&sortOrder=ASC" +
                "&sortField=id"

        val request = Request.Builder()
            .url(url)
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()

        val response = client.newCall(request).execute()
        val json = response.body?.string() ?: throw Exception("Aucune réponse de l'API")
        println("Réponse JSON brute : $json")

        if (json.contains("\"message\"")) {
            throw Exception("Erreur API : $json")
        }

        val imdbResponse = Gson().fromJson(json, IMDbResponse::class.java)
        println("Films désérialisés : ${imdbResponse.results}")
        return imdbResponse.results
    }
}