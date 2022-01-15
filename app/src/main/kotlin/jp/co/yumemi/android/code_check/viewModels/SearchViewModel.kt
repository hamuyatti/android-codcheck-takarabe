/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.viewModels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.repository.SearchRepository
import kotlinx.coroutines.*
import kotlinx.parcelize.Parcelize
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date>
        get() = _lastSearchDate

    private val _repositoryList = MutableLiveData<List<Repository>>()
    val repositoryList: LiveData<List<Repository>>
        get() = _repositoryList

    private val _errorLD = MutableLiveData(false)
    val errorLD: LiveData<Boolean>
        get() = _errorLD

    val state = searchRepository.state

    fun searchResults(inputText: String) {
        _errorLD.value = false
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.fetchRepository(inputText = inputText)
            _lastSearchDate.postValue(Date())
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