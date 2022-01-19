/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.repository.SearchRepository
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _lastSearchDate = MutableLiveData<Date>()
    val lastSearchDate: LiveData<Date>
        get() = _lastSearchDate

    private val _isVisible = MutableLiveData(false)
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private var sortKind = MutableLiveData(SORT_UPDATED)

    val state = searchRepository.state

    fun fetchRepositories(inputText: String) {
        _isVisible.value = true
        viewModelScope.launch {
            searchRepository.fetchRepositories(
                searchText = inputText,
                sort = sortKind.value ?: SORT_UPDATED
            )
            _lastSearchDate.postValue(Date())
        }
    }

    fun onClickUpdatedRepository() {
        sortKind.value = SORT_UPDATED
    }

    fun onClickNotUpdatedRepository() {
        sortKind.value = SORT_NOT_UPDATED
    }

    fun onClickPopularRepository() {
        sortKind.value = SORT_POPULAR
    }

    fun setVisible(isVisible: Boolean) {
        _isVisible.value = isVisible
    }

    companion object {
        const val SORT_UPDATED = "updated"
        const val SORT_NOT_UPDATED = "updated-asc"
        const val SORT_POPULAR = "reactions"
    }
}