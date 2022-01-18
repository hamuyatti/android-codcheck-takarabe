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

    val state = searchRepository.state

    fun searchResults(inputText: String) {
        _isVisible.value = true
        viewModelScope.launch{
            searchRepository.fetchRepository(searchText = inputText)
            _lastSearchDate.postValue(Date())
        }
    }

    fun setVisible(isVisible: Boolean){
        _isVisible.value = isVisible
    }
}