package uz.gita.dictionaryAppMVVM.presenter.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.SplashViewModel

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    override val navigateDictionaryScreenLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2500)
            navigateDictionaryScreenLD.postValue(Unit)
        }
    }

}