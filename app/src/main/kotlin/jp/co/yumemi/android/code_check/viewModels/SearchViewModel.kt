/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.viewModels

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import kotlinx.coroutines.*
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class SearchViewModel() : ViewModel() {

    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date>
        get() = _lastSearchDate

    private val _repositoryList = MutableLiveData<List<Repository>>()
    val repositoryList: LiveData<List<Repository>>
        get() = _repositoryList

    private val _errorLD = MutableLiveData(false)
    val errorLD: LiveData<Boolean>
        get() = _errorLD

    // 検索結果
    fun searchResults(inputText: String) {
        _errorLD.value = false
        viewModelScope.launch(Dispatchers.IO) {
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
                _lastSearchDate.postValue(Date())
                _repositoryList.postValue(items)
            } catch (e: Exception) {
                _errorLD.postValue(true)
            }
        }
    }
}

@Parcelize
data class Repository(
    val name: String,
    val ownerIconUrl: String?,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable