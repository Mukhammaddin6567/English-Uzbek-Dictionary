package uz.gita.dictionaryAppMVVM.presenter

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData
import uz.gita.dictionaryAppMVVM.databinding.ScreenFavouriteBinding
import uz.gita.dictionaryAppMVVM.presenter.adapter.DictionaryCursorAdapter
import uz.gita.dictionaryAppMVVM.presenter.dialog.DialogItem
import uz.gita.dictionaryAppMVVM.presenter.impl.FavouriteViewModelImpl
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.DictionaryViewModel
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.FavouriteViewModel
import uz.gita.dictionaryAppMVVM.utils.onClick

class FavouriteScreen : Fragment(R.layout.screen_favourite), SearchView.OnQueryTextListener {

    private val binding by viewBinding(ScreenFavouriteBinding::bind)
    private val viewModel: FavouriteViewModel by viewModels<FavouriteViewModelImpl>()
    private val adapter: DictionaryCursorAdapter by lazy { DictionaryCursorAdapter() }
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated")
        activity?.let { it.title = resources.getString(R.string.text_favourite) }
        subscribeViewBinding(binding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(binding: ScreenFavouriteBinding) = with(binding) {
        Timber.d("subscribeViewBinding")
        adapter.setOnClickFavouriteListener { state, itemId ->
            viewModel.setFavourite(state, itemId)
        }

        adapter.setOnItemClickListener {
            viewModel.onClickItem(it)
        }

        listWords.adapter = adapter
        FastScrollerBuilder(listWords).build()
        listWords.layoutManager = LinearLayoutManager(requireContext())

        stateLanguage.onClick {
            viewModel.onLanguageChanged()
        }

        searchWord.setOnQueryTextListener(this@FavouriteScreen)
        searchWord.setOnCloseListener {
            viewModel.onClickCloseButton()
            return@setOnCloseListener false
        }
    }

    private fun subscribeViewModel(viewModel: DictionaryViewModel) = with(viewModel) {
        Timber.d("subscribeViewModel")
        allWords.observe(viewLifecycleOwner) { allWordsObserver(it) }
        allWordsByQuery.observe(viewLifecycleOwner) { allWordsByQueryObserver(it) }
        clickedItem.observe(viewLifecycleOwner) { clickedItemObserver(it) }
        searchQuery.observe(viewLifecycleOwner) { searchQueryObserver(it) }
        closeButton.observe(viewLifecycleOwner) { closeButtonObserver() }
        language.observe(viewLifecycleOwner) { languageObserver(it) }
        noResult.observe(viewLifecycleOwner) { noResultObserver(it) }
    }

    private fun noResultObserver(state: Boolean) = with(binding) {
        when (state) {
            true -> {
                listWords.visibility = View.VISIBLE
                placeholder.visibility = View.GONE
            }
            else -> {
                listWords.visibility = View.GONE
                placeholder.visibility = View.VISIBLE
            }
        }
    }

    private fun allWordsObserver(cursor: Cursor) {
        Timber.d("allWordsObserver: $cursor")
        adapter.cursor = cursor
        adapter.notifyDataSetChanged()
    }

    private fun allWordsByQueryObserver(cursor: Cursor) {
        Timber.d("allWordsByQuery: $cursor")
        adapter.cursor = cursor
        adapter.notifyDataSetChanged()
    }

    private fun clickedItemObserver(data: DialogItemData) {
        val dialog = DialogItem(data)
        dialog.show(childFragmentManager, "")

    }

    private fun searchQueryObserver(query: String?) {
        Timber.d("searchQueryObserver: $query")
        query?.let { adapter.query = it }
    }

    private fun closeButtonObserver() = with(binding) {
        Timber.d("closeButtonObserver")
        handler.removeCallbacksAndMessages(null)
        searchWord.setQuery("", false)
        searchWord.clearFocus()
    }

    private fun languageObserver(language: Boolean) {
        when (language) {
            false -> {
                binding.apply {
                    textMainLanguage.text = resources.getString(R.string.text_english)
                    textTranslateLanguage.text = resources.getString(R.string.text_uzbek)
                }
            }
            else -> {
                binding.apply {
                    textMainLanguage.text = resources.getString(R.string.text_uzbek)
                    textTranslateLanguage.text = resources.getString(R.string.text_english)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Timber.d("onQueryTextSubmit")
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModel.allWordsByQuery(query = "%$query%")
            viewModel.onSearch(query = query)
        }, 500)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Timber.d("onQueryTextChange")
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModel.allWordsByQuery(query = "%$newText%")
            viewModel.onSearch(query = newText)
        }, 500)
        return true
    }

}