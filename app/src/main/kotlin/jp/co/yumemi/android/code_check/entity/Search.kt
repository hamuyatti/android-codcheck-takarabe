package jp.co.yumemi.android.code_check.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Search(
    @SerialName("total_count")val totalCount: Int,
    @SerialName("incomplete_results")val incompleteResults: Boolean,
    @SerialName("items")val items: List<RepositoryInfo>
)
