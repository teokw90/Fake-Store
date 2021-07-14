package com.kw.fakeStore.base.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.kw.fakeStore.R

class SplashScreenFragment : Fragment() {
    private val mainActivity: AppCompatActivity by lazy {
        requireActivity() as AppCompatActivity
    }

    private val supportActionBar: ActionBar? by lazy {
        mainActivity.supportActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onStart() {
        super.onStart()

        mainActivity.run {
            supportActionBar?.run {
                hide()
            }
        }

        Handler().postDelayed({
            val destination = SplashScreenFragmentDirections.actionSplashScreenFragmentToNavGraphStore()
            findNavController().navigate(destination)
        }, 3000)
    }

    override fun onStop() {
        supportActionBar?.run {
            show()
        }
        super.onStop()
    }
}