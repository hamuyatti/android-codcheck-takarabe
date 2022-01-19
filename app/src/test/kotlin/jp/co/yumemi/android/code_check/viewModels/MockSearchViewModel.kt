package jp.co.yumemi.android.code_check.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_NOT_UPDATED
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_POPULAR
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_UPDATED
import kotlinx.coroutines.launch
import java.util.*

class MockSearchViewModel : ViewModel() {
    private var _sortKind = MutableLiveData(SORT_UPDATED)
    val sortKind: LiveData<String>
        get() = _sortKind

    fun onClickUpdatedRepository() {
        _sortKind.value = SORT_UPDATED
    }

    fun onClickNotUpdatedRepository() {
        _sortKind.value = SORT_NOT_UPDATED

    }

    fun onClickPopularRepository() {
        _sortKind.value = SORT_POPULAR
    }
}