package uz.gita.dictionaryAppMVVM

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.databinding.ActivityMainBinding
import uz.gita.dictionaryAppMVVM.presenter.dialog.DialogAbout
import uz.gita.dictionaryAppMVVM.utils.hideKeyboard
import uz.gita.dictionaryAppMVVM.utils.onClick

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.visibility = VISIBLE
        setSupportActionBar(binding.toolbar)

        // fragments container
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).findNavController()
        navController.addOnDestinationChangedListener(this)
        // toggle button
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.draweryLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.draweryLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                hideKeyboard()
            }

            override fun onDrawerOpened(drawerView: View) {
                hideKeyboard()
            }

            override fun onDrawerClosed(drawerView: View) {
                drawerView.requestFocus()
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
        binding.draweryLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        with(binding) {
            navigationLayout.apply {
                buttonDictionary.onClick {
                    if (binding.draweryLayout.isDrawerOpen(GravityCompat.START))
                        binding.draweryLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.dictionaryScreen)
                }
                buttonFavourite.onClick {
                    if (binding.draweryLayout.isDrawerOpen(GravityCompat.START))
                        binding.draweryLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.favouriteScreen)
                }
                buttonAbout.onClick {
                    binding.draweryLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                    val dialog = DialogAbout()
                    dialog.show(supportFragmentManager, "")
                    dialog.setOnClickOkListener {
                        binding.draweryLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.draweryLayout.isDrawerOpen(GravityCompat.START))
            binding.draweryLayout.closeDrawer(GravityCompat.START)
        else {
            if (navController.currentDestination?.id == R.id.dictionaryScreen ||
                navController.currentDestination?.id == R.id.favouriteScreen
            ) {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Snackbar.make(binding.root, "Press back again to exit", Snackbar.LENGTH_SHORT)
                        .apply {
                            setBackgroundTint(
                                ContextCompat.getColor(
                                    this@MainActivity,
                                    R.color.color_status_bar
                                )
                            )
                        }
                        .show()
                }
            } else finish()
            pressedTime = System.currentTimeMillis()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.splashScreen) {
            Timber.d("TTT Locked")
            binding.draweryLayout.setDrawerLockMode(
                LOCK_MODE_LOCKED_CLOSED
            )
        } else {
            Timber.d("TTT Unlocked")
            binding.draweryLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        }
    }
}