package uz.gita.dictionaryAppMVVM.presenter.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData
import uz.gita.dictionaryAppMVVM.domain.Repository
import uz.gita.dictionaryAppMVVM.domain.impl.RepositoryImpl
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.DictionaryViewModel

class DictionaryViewModelImpl : ViewModel(), DictionaryViewModel {

    private val repository: Repository by lazy { RepositoryImpl() }

    override val allWords: MutableLiveData<Cursor> by lazy { MutableLiveData<Cursor>() }
    override val allWordsByQuery: MutableLiveData<Cursor> by lazy { MutableLiveData<Cursor>() }
    override val clickedItem: MutableLiveData<DialogItemData> by lazy { MutableLiveData<DialogItemData>() }
    override val searchQuery: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val closeButton: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val language: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val noResult: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private var currentQuery: String? = null

    init {
        initWords()
    }

    private fun initWords() {
        language.value = repository.getCurrentLanguage()
        when (repository.getCurrentLanguage()) {
            false -> {
                val data = repository.getAllEnglishWords()
                allWords.value = data
                noResult.value = data.count > 0
            }
            else -> {
                val data = repository.getAllUzbekWords()
                allWords.value = data
                noResult.value = data.count > 0
            }
        }
    }

    override fun allWordsByQuery(query: String?) {
        query?.let {
            when (repository.getCurrentLanguage()) {
                false -> {
                    val data = repository.getAllEnglishWordsByQuery("%${it}%")
                    allWordsByQuery.value = data
                    noResult.value = data.count > 0
                }
                else -> {
                    val data = repository.getAllUzbekWordsByQuery("%${it}%")
                    allWordsByQuery.value = data
                    noResult.value = data.count > 0
                }
            }
        }
    }

    override fun onClickItem(id: Int) {
        clickedItem.value = repository.getItemById(id)
    }

    override fun onSearch(query: String) {
        searchQuery.value = query
        currentQuery = query
    }

    override fun onClickCloseButton() {
        closeButton.value = Unit
        searchQuery.value = null
        currentQuery = null
    }


    override fun onLanguageChanged() {
        repository.setCurrentLanguage(!repository.getCurrentLanguage())
        onClickCloseButton()
        initWords()
    }

    override fun setFavourite(state: Int, id: Int) {
        repository.setFavourite(state, id)
        when (currentQuery) {
            null -> initWords()
            else -> allWordsByQuery(currentQuery!!)
        }
    }

}