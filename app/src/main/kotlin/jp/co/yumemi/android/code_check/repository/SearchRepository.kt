package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_UPDATED
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray

interface SearchRepository {
    val state: StateFlow<Resource<List<RepositoryInfo>>>
    suspend fun fetchRepositories(searchText: String, sort:String = SORT_UPDATED)
}