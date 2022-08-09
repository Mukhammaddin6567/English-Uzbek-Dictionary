package uz.gita.dictionaryAppMVVM.presenter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.databinding.DialogAboutBinding
import uz.gita.dictionaryAppMVVM.utils.onClick


class DialogAbout() : DialogFragment(R.layout.dialog_about) {

    private val binding by viewBinding(DialogAboutBinding::bind)
    private var onClickOkListener: (() -> Unit)? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        textWord.text = resources.getString(R.string.text_about_detailed)

        buttonOk.onClick {
            onClickOkListener?.invoke()
            dismiss()
        }

        isCancelable = false
    }

    fun setOnClickOkListener(block: () -> Unit) {
        onClickOkListener = block
    }
}