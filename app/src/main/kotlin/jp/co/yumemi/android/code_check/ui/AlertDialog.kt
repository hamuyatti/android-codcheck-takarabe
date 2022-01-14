package jp.co.yumemi.android.code_check.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import jp.co.yumemi.android.code_check.R

class AlertDialog : DialogFragment(){
    companion object {
        private const val BUNDLE_KEY_TITLE = "bundle_key_title"
        private const val BUNDLE_KEY_MESSAGE = "bundle_key_message"

        fun showDialog(
            title: String,
            message: String,
            fragmentManager: FragmentManager,
            tag: String
        ) {
            newInstance(title, message).apply {
                show(fragmentManager, tag)
            }
        }

        private fun newInstance(title: String, message: String): DialogFragment {
            return DialogFragment().apply {
                arguments = bundleOf(
                    Pair(BUNDLE_KEY_TITLE, title),
                    Pair(BUNDLE_KEY_MESSAGE, message)
                )
            }
        }
    }

    private lateinit var title: String
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString(BUNDLE_KEY_TITLE) ?: return
        message = requireArguments().getString(BUNDLE_KEY_MESSAGE) ?: return
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.close)) { _, _ ->
                this.dismiss()
            }
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        this.dismiss()
    }
}
