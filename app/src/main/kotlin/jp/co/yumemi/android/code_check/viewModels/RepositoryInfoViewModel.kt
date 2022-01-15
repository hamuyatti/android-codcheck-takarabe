package jp.co.yumemi.android.code_check.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.R
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun init(repository: Repository) {
        this.repositoryInfo.value = repository
    }

    private val repositoryInfo = MutableLiveData<Repository>()
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
            fun from(context: Context, repository: Repository) = RenderData(
                repository.name,
                repository.ownerIconUrl,
                checkIsNull(context = context, text = repository.language),
                context.getString(R.string.addition_stars, repository.stargazersCount),
                context.getString(R.string.addition_watchers, repository.watchersCount),
                context.getString(R.string.addition_forks, repository.forksCount),
                context.getString(R.string.addition_openIssuesCount, repository.openIssuesCount)
            )

            private fun checkIsNull(context: Context, text: String): String {
                return if (text == context.getString(R.string.null_string)) {
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