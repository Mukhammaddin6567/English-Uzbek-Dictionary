package uz.gita.dictionaryAppMVVM.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.dictionaryAppMVVM.R
import uz.gita.dictionaryAppMVVM.presenter.impl.SplashViewModelImpl
import uz.gita.dictionaryAppMVVM.presenter.viewmodel.SplashViewModel


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) = with(viewModel) {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        super.onCreate(savedInstanceState)
        navigateDictionaryScreenLD.observe(this@SplashScreen) {
            findNavController().navigate(R.id.action_splashScreen_to_dictionaryScreen)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}