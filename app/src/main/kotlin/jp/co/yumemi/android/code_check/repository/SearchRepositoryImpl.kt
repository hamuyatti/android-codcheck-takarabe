package jp.co.yumemi.android.code_check.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class SearchRepositoryImpl : SearchRepository {

    private val _state = MutableStateFlow<Resource<List<RepositoryInfo>>>(Resource.Empty)
    override val state: StateFlow<Resource<List<RepositoryInfo>>> = _state

    override suspend fun fetchRepository(inputText: String) {
        try {
            val client = HttpClient(Android)
            val response: HttpResponse =
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }
            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items")
            val items = mutableListOf<RepositoryInfo>()

            jsonItems?.let {
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    items.add(
                        RepositoryInfo(
                            name = jsonItem.optString("full_name"),
                            ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url"),
                            language = jsonItem.optString("language"),
                            stargazersCount = jsonItem.optLong("stargazers_count"),
                            watchersCount = jsonItem.optLong("watchers_count"),
                            forksCount = jsonItem.optLong("forks_count"),
                            openIssuesCount = jsonItem.optLong("open_issues_count")
                        )
                    )
                }
                _state.value = Resource.Success(items)
            }
        } catch (e: Exception) {

        }
    }
}