package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray

interface SearchRepository {
    val state: StateFlow<Resource<List<RepositoryInfo>>>
    suspend fun fetchRepository(searchText: String)
    fun makeRepositoryInfo(jsonItems: JSONArray?):List<RepositoryInfo>
}