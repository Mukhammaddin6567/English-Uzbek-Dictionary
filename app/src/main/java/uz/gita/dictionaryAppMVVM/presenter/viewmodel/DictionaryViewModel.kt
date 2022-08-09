package uz.gita.dictionaryAppMVVM.presenter.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData

interface DictionaryViewModel {

    val allWords: LiveData<Cursor>
    val allWordsByQuery: LiveData<Cursor>
    val clickedItem: LiveData<DialogItemData>
    val searchQuery: LiveData<String>
    val closeButton: LiveData<Unit>
    val language: LiveData<Boolean>
    val noResult: LiveData<Boolean>

    fun allWordsByQuery(query: String?)

    fun onClickItem(id: Int)

    fun onSearch(query: String)

    fun onClickCloseButton()

    fun onLanguageChanged()

    fun setFavourite(state: Int, id: Int)

}