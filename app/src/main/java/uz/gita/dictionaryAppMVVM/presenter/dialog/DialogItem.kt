package uz.gita.dictionaryAppMVVM.presenter.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData
import uz.gita.dictionaryAppMVVM.databinding.DialogItemBinding
import uz.gita.dictionaryAppMVVM.utils.onClick


class DialogItem(private val dataDialog: DialogItemData) : DialogFragment(R.layout.dialog_item) {

    private val binding by viewBinding(DialogItemBinding::bind)
    private var onClickFavouriteListener: ((Boolean) -> Unit)? = null
//    private var status = false

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        textWord.text = dataDialog.word
        textTranscript.text = resources.getString(
            R.string.text_transcript,
            dataDialog.transcript?.let { it.substring(1, it.length - 1) })
        textTranslate.text = resources.getString(R.string.text_translate, dataDialog.translate)
        textType.text = resources.getString(R.string.text_type, dataDialog.type)
//        isFavourite.isChecked = when (dataDialog.isFavourite) {
//            0 -> false
//            else -> true
//        }
        if (dataDialog.countable.isNullOrEmpty()) textCountable.visibility = GONE
        else {
            textCountable.visibility = VISIBLE
            textCountable.text = resources.getString(R.string.text_countable, dataDialog.countable)
        }
//        status = isFavourite.isChecked
//        isFavourite.setOnCheckedChangeListener { _, status ->
//            this@DialogItem.status = status
//        }
        buttonOk.onClick {
//            onClickFavouriteListener?.invoke(this@DialogItem.status)
            dismiss()
        }
        isCancelable = false
    }

   private fun setOnClickFavourite(block: (Boolean) -> Unit) {
        onClickFavouriteListener = block
    }
}