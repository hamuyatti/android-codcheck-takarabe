package jp.co.yumemi.android.code_check.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.json.JSONArray
import org.json.JSONObject

class ApiImpl : Api {
    override suspend fun fetchRepository(searchText: String): JSONArray? {
        val client = HttpClient(Android)
        val response: HttpResponse =
            client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", searchText)
            }
        val jsonBody = JSONObject(response.receive<String>())
        return jsonBody.optJSONArray("items")
    }
}