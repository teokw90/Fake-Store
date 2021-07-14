package com.kw.fakeStore.base.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.kw.fakeStore.R
import com.kw.fakeStore.databinding.ActivityMainBinding
import com.kw.fakeStore.databinding.BackdropMenuBinding
import com.kw.fakeStore.di.injector.Injectable
import com.kw.fakeStore.utils.NavigationIconClickListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity: AppCompatActivity(), HasAndroidInjector, Injectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private lateinit var viewDataBinding: ActivityMainBinding
    private lateinit var includeViewDataBinding: BackdropMenuBinding

    private val navigationIconClickListener by lazy {
        NavigationIconClickListener(
            this@MainActivity,
            viewDataBinding.appBar,
            viewDataBinding.fragmentContainer,
            AccelerateDecelerateInterpolator(),
            ContextCompat.getDrawable(this@MainActivity.baseContext!!, R.drawable.fake_store_menu),
            ContextCompat.getDrawable(this@MainActivity.baseContext!!, R.drawable.fake_store_close)
        )
    }

    private val exitAppAlertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle(R.string.exit_app_title)
            setMessage(R.string.exit_app_message)
            setPositiveButton(R.string.exit_app_exit) { dialog, _ ->
                dialog.dismiss()
                finishAffinity()
            }

            setNegativeButton(R.string.exit_app_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navInflater: NavInflater
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewDataBinding.root
        setContentView(view)

        includeViewDataBinding = viewDataBinding.backdropMenu

        initAppBar()
        initNavigationController()
        setupListener()
    }

    private fun initAppBar() {
        setSupportActionBar(viewDataBinding.appBar)
        viewDataBinding.appBar.setNavigationOnClickListener(navigationIconClickListener)

    }

    private fun initNavigationController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navInflater = navController.navInflater
        navGraph = navInflater.inflate(R.navigation.nav_graph_store)
    }

    private fun setupListener() = with(includeViewDataBinding) {
        storeButton.setOnClickListener {
            navigationIconClickListener.onClick()
            if (navController.currentDestination?.id != R.id.productsCatalogFragment) {
                navGraph.startDestination = R.id.productsCatalogFragment
                navController.apply {
                    graph = navGraph
                }
            }
        }

        myCartButton.setOnClickListener {
            navigationIconClickListener.onClick()
            if (navController.currentDestination?.id != R.id.myCartFragment) {
                navGraph.startDestination = R.id.myCartFragment
                navController.apply {
                    graph = navGraph
                }
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.productsCatalogFragment) {
            if (exitAppAlertDialog.isShowing.not()) exitAppAlertDialog.show()
        } else {
            super.onBackPressed()
        }
    }
}