package jp.co.yumemi.android.code_check.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.viewModels.Repository
import org.json.JSONObject

class SearchRepositoryImpl:SearchRepository {
    override suspend fun fetchRepository(inputText:String) {
        try {
            val client = HttpClient(Android)
            val response: HttpResponse =
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }
            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items")
            val items = mutableListOf<Repository>()

            jsonItems?.let {
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    val name = jsonItem.optString("full_name")
                    val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url")
                    val language = jsonItem.optString("language")
                    val stargazersCount = jsonItem.optLong("stargazers_count")
                    val watchersCount = jsonItem.optLong("watchers_count")
                    val forksCount = jsonItem.optLong("forks_count")
                    val openIssuesCount = jsonItem.optLong("open_issues_count")

                    items.add(
                        Repository(
                            name = name,
                            ownerIconUrl = ownerIconUrl,
                            language = language,
                            stargazersCount = stargazersCount,
                            watchersCount = watchersCount,
                            forksCount = forksCount,
                            openIssuesCount = openIssuesCount
                        )
                    )
                }

            }
        }(e: Exception) {
            _errorLD.postValue(true)
        }
    }
}