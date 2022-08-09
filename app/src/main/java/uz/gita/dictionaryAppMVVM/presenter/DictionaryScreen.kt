package uz.gita.dictionaryAppMVVM.presenter

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData
import uz.gita.dictionaryAppMVVM.databinding.ScreenDictionaryBinding
import uz.gita.dictionaryAppMVVM.presenter.adapter.DictionaryCursorAdapter
import uz.gita.dictionaryAppMVVM.presenter.dialog.DialogItem
import uz.gita.dictionaryAppMVVM.presenter.impl.DictionaryViewModelImpl
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.DictionaryViewModel
import uz.gita.dictionaryAppMVVM.utils.onClick

class DictionaryScreen : Fragment(R.layout.screen_dictionary), SearchView.OnQueryTextListener {

    private val binding by viewBinding(ScreenDictionaryBinding::bind)
    private val viewModel: DictionaryViewModel by viewModels<DictionaryViewModelImpl>()
    private val adapter: DictionaryCursorAdapter by lazy { DictionaryCursorAdapter() }
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Timber.d("onViewCreated")
        activity?.let { it.title = resources.getString(R.string.text_dictionary) }
        subscribeViewBinding(binding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(binding: ScreenDictionaryBinding) = with(binding) {
        Timber.d("subscribeViewBinding")
        adapter.setOnClickFavouriteListener { state, id ->
            viewModel.setFavourite(state, id)
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

        searchWord.setOnQueryTextListener(this@DictionaryScreen)
        searchWord.setOnCloseListener {
            viewModel.onClickCloseButton()
            return@setOnCloseListener true
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
                listWords.visibility = VISIBLE
                placeholder.visibility = GONE
            }
            else -> {
                listWords.visibility = GONE
                placeholder.visibility = VISIBLE
            }
        }
    }

    private fun allWordsObserver(cursor: Cursor) {
        Timber.d("allWordsObserver: $cursor")
        adapter.cursor = cursor
        adapter.notifyItemRangeChanged(0, cursor.count)
//        adapter.notifyDataSetChanged()
    }

    private fun allWordsByQueryObserver(cursor: Cursor) {
        Timber.d("allWordsByQuery: $cursor")
        adapter.cursor = cursor
        adapter.notifyItemRangeChanged(0, cursor.count)
//        adapter.notifyDataSetChanged()
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
        searchWord.setQuery(null, false)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.d("onQueryTextSubmit")
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModel.allWordsByQuery(query = query)
            query?.let { viewModel.onSearch(query = it) }
        }, 500)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.d("onQueryTextChange")
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModel.allWordsByQuery(query = newText)
            newText?.let { viewModel.onSearch(query = it) }
        }, 500)
        return true
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

}