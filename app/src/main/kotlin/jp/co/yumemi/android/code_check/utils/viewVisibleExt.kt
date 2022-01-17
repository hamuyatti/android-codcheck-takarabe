package jp.co.yumemi.android.code_check.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean){
    this.isVisible = visible
}