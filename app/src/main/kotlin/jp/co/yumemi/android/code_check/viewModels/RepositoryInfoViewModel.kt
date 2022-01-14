package jp.co.yumemi.android.code_check.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.R
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(
    private val application: Application
):ViewModel(){
    fun makeDisplayText(baseLong: Long,addedText:String):String{
        return "${baseLong}${addedText}"
    }

    fun checkIsNull(text: String): String {
        return if (text == application.getString(R.string.null_string)) {
            application.getString(R.string.not_set_language)
        } else {
            application.getString(
                R.string.written_language,
                text
            )
        }
    }
}