package jp.co.yumemi.android.code_check.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class RepositoryInfo(
    val name: String,
    val language: String,
    @SerialName("avatar_url") val ownerIconUrl: String?,
    @SerialName("stargazers_count")val stargazersCount: Long,
    @SerialName("watchers_count") val watchersCount: Long,
    @SerialName("forks_count") val forksCount: Long,
    @SerialName("open_issues_count") val openIssuesCount: Long,
) : Parcelable
