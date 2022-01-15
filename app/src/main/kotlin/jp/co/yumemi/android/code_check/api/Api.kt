package jp.co.yumemi.android.code_check.api

import org.json.JSONArray

interface Api {
    suspend fun fetchRepository(searchText: String): JSONArray?
}