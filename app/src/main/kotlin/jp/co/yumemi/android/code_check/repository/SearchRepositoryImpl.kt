package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.webApi.Api
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import jp.co.yumemi.android.code_check.utils.asyncFetch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

class SearchRepositoryImpl(private val api: Api) : SearchRepository {

    private val _state = MutableStateFlow<Resource<List<RepositoryInfo>>>(Resource.Empty)
    override val state: StateFlow<Resource<List<RepositoryInfo>>> = _state

    override suspend fun fetchRepositories(searchText: String, sort: String) {
        withContext(Dispatchers.IO) {
            try {
                _state.value = Resource.Success(asyncFetch {
                    api.fetchRepositories(
                        query = searchText,
                        sort = sort
                    )
                }.items)
            } catch (e: IOException) {
                _state.value = Resource.Failed(e)
            } catch (e: Exception) {
                _state.value = Resource.Failed(e)
            }
        }
    }
}