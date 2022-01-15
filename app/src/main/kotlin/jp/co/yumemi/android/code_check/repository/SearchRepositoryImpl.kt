package jp.co.yumemi.android.code_check.repository

import io.ktor.http.*
import jp.co.yumemi.android.code_check.api.Api
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import jp.co.yumemi.android.code_check.entity.Resource
import jp.co.yumemi.android.code_check.utils.asyncFetch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException

class SearchRepositoryImpl(private val api: Api) : SearchRepository {

    private val _state = MutableStateFlow<Resource<List<RepositoryInfo>>>(Resource.Empty)
    override val state: StateFlow<Resource<List<RepositoryInfo>>> = _state

    override suspend fun fetchRepository(searchText: String) {
        withContext(Dispatchers.IO) {
            try {
                _state.value = Resource.Success(asyncFetch { api.findRepositories(query = searchText)}.items)
            } catch (e: IOException) {
                _state.value = Resource.Failed(e)
            } catch (e: Exception) {
                Timber.d("${e.message}")
                _state.value = Resource.Failed(e)
            }
        }
    }
}