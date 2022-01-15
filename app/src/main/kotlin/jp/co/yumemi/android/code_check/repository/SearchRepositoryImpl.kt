package jp.co.yumemi.android.code_check.repository

import io.ktor.http.*
import jp.co.yumemi.android.code_check.api.Api
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.IOException

class SearchRepositoryImpl(private val api: Api) : SearchRepository {

    private val _state = MutableStateFlow<Resource<List<RepositoryInfo>>>(Resource.Empty)
    override val state: StateFlow<Resource<List<RepositoryInfo>>> = _state

    override suspend fun fetchRepository(searchText: String) {
        withContext(Dispatchers.IO) {
            try {
                val jsonItems = api.fetchRepository(searchText)
                _state.value = Resource.Success(makeRepositoryInfo(jsonItems))
            } catch (e: IOException) {
                _state.value = Resource.Failed(e)
            } catch (e: Exception) {
                _state.value = Resource.Failed(e)
            }
        }
    }

    override fun makeRepositoryInfo(jsonItems: JSONArray?): List<RepositoryInfo> {
        val items = mutableListOf<RepositoryInfo>()
        jsonItems?: return emptyList()
        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            items.add(
                RepositoryInfo(
                    name = jsonItem.optString("full_name"),
                    ownerIconUrl = jsonItem.optJSONObject("owner")
                        ?.optString("avatar_url"),
                    language = jsonItem.optString("language"),
                    stargazersCount = jsonItem.optLong("stargazers_count"),
                    watchersCount = jsonItem.optLong("watchers_count"),
                    forksCount = jsonItem.optLong("forks_count"),
                    openIssuesCount = jsonItem.optLong("open_issues_count")
                )
            )
        }
        return items
    }

}