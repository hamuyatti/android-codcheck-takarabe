package jp.co.yumemi.android.code_check.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class ImageViewExt {
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView?, urlString: String) {
        view ?: return
        view.load(urlString)
    }
}