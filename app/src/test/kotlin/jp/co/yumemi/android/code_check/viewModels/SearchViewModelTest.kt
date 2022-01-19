package jp.co.yumemi.android.code_check.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.spyk
import io.mockk.verify
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_NOT_UPDATED
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_POPULAR
import jp.co.yumemi.android.code_check.viewModels.SearchViewModel.Companion.SORT_UPDATED
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MockSearchViewModel
    @Before
    fun setUp() {
        viewModel = MockSearchViewModel()
    }

    @Test
    fun onClickUpdatedRepository() {
        val mockObserver = spyk<Observer<String>>()
        viewModel.sortKind.observeForever(mockObserver)

        viewModel.onClickUpdatedRepository()
        verify {
            mockObserver.onChanged(SORT_UPDATED)
        }
    }

    @Test
    fun onClickNotUpdatedRepository() {
        val mockObserver = spyk<Observer<String>>()
        viewModel.sortKind.observeForever(mockObserver)

        viewModel.onClickNotUpdatedRepository()
        verify {
            mockObserver.onChanged(SORT_NOT_UPDATED)
        }
    }

    @Test
    fun onClickPopularRepository() {
        val mockObserver = spyk<Observer<String>>()
        viewModel.sortKind.observeForever(mockObserver)

        viewModel.onClickPopularRepository()
        verify() {
            mockObserver.onChanged(SORT_POPULAR)
        }
    }
}