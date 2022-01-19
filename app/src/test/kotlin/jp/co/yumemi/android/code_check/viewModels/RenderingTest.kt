package jp.co.yumemi.android.code_check.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import io.mockk.spyk
import io.mockk.verify
import jp.co.yumemi.android.code_check.entity.Owner
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RenderingTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RepositoryInfoViewModel

    @Before
    fun setUp() {
        viewModel = RepositoryInfoViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun checkStarTextIsOk() {
        val mockObserver = spyk<Observer<RepositoryInfoViewModel.RenderData>>()
        viewModel.renderData.observeForever(mockObserver)

        viewModel.init(mockRepositoryInfo)

        verify {
            mockObserver.onChanged(mockRenderData)
        }
    }

    private val mockRepositoryInfo = RepositoryInfo(
        name = "hamuyatti", id = 1, language = null, owner = Owner(null, 0), 0, 0, 0, 0
    )
    private val mockRenderData = RepositoryInfoViewModel.RenderData(
        name = "hamuyatti",
        ownerIconUrl = null,
        language = "言語が指定されていません",
        stargazersCountText = "0 stars",
        watchersCountText = "0 watchers",
        forksCountText = "0 forks",
        openIssuesCountText = "0 open issues"
    )

}