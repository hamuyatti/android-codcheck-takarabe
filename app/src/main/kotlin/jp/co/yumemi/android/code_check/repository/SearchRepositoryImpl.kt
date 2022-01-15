package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.api.Api
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.io.IOException

class SearchRepositoryImpl(private val api: Api) : SearchRepository {

    private val _state = MutableStateFlow<Resource<List<RepositoryInfo>>>(Resource.Empty)
    override val state: StateFlow<Resource<List<RepositoryInfo>>> = _state

    override suspend fun fetchRepository(searchText: String) {
        try {
            val jsonItems = api.fetchRepository(searchText)
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
        }
    }
}