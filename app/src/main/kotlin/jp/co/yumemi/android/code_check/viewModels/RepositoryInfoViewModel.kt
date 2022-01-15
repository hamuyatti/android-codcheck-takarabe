package jp.co.yumemi.android.code_check.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.entity.RepositoryInfo
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun init(repositoryInfo: RepositoryInfo) {
        this.repositoryInfo.value = repositoryInfo
    }

    private val repositoryInfo = MutableLiveData<RepositoryInfo>()
    val renderData = repositoryInfo.map {
        RenderData.from(application, it)
    }

    data class RenderData(
        val name: String,
        val ownerIconUrl: String?,
        val language: String,
        val stargazersCountText: String,
        val watchersCountText: String,
        val forksCountText: String,
        val openIssuesCountText: String,
    ) {
        companion object {
            fun from(context: Context, repositoryInfo: RepositoryInfo) = RenderData(
                repositoryInfo.name,
                repositoryInfo.owner.ownerIconUrl,
                checkIsNull(context = context, text = repositoryInfo.language),
                context.getString(R.string.addition_stars, repositoryInfo.stargazersCount),
                context.getString(R.string.addition_watchers, repositoryInfo.watchersCount),
                context.getString(R.string.addition_forks, repositoryInfo.forksCount),
                context.getString(R.string.addition_openIssuesCount, repositoryInfo.openIssuesCount)
            )

            private fun checkIsNull(context: Context, text: String?): String {
                return if (text == null) {
                    context.getString(R.string.not_set_language)
                } else {
                    context.getString(
                        R.string.written_language,
                        text
                    )
                }
            }
        }
    }
}