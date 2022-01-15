package jp.co.yumemi.android.code_check.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RepositoryInfo(
    @SerialName("name")val name: String,
    @SerialName("language")val language: String,
    @SerialName("owner") val owner: Owner,
    @SerialName("stargazers_count")val stargazersCount: Long,
    @SerialName("watchers_count") val watchersCount: Long,
    @SerialName("forks_count") val forksCount: Long,
    @SerialName("open_issues_count") val openIssuesCount: Long,
) : Parcelable
