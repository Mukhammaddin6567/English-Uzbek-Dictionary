package uz.gita.dictionaryAppMVVM.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


fun View.onClick(action: (View) -> Unit) {
    setOnClickListener {
        it?.let(action)
    }
}

fun String.paintResult(query: String): Spannable {
    var start = this.indexOf(query)
//    Timber.d("TTT $start")
    var end = start + query.length
//    Timber.d("TTT $end")
    if (this.equals(query, true)) {
        start = 0
        end = this.length
    }
    val spannable = SpannableString(this)
    spannable.setSpan(
        ForegroundColorSpan(Color.BLUE),
        start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view:View){
}


