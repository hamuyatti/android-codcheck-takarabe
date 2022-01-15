package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.entity.Resource
import jp.co.yumemi.android.code_check.viewModels.Repository
import kotlinx.coroutines.flow.StateFlow

interface SearchRepository {
    val state: StateFlow<Resource<List<Repository>>>
    suspend fun fetchRepository(inputText:String)
}