package jp.co.yumemi.android.code_check.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Owner(
    @SerialName("avatar_url")
    val ownerIconUrl: String?,
    @SerialName("id")
    val id: Int?
) : Parcelable
