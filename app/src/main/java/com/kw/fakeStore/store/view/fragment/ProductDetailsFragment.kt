package com.kw.fakeStore.store.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kw.fakeStore.R
import com.kw.fakeStore.databinding.FragmentProductDetailsBinding
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.view.adapter.decoration.ProductGridItemDecoration
import com.kw.fakeStore.store.viewModel.StoreViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductDetailsFragment: Fragment(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: ProductDetailsFragmentArgs by navArgs()
    private val productInfo: ProductWithQuantityInCart by lazy {
        args.productInfo
    }

    private lateinit var storeViewModel: StoreViewModel
    private lateinit var viewDataBinding: FragmentProductDetailsBinding

    private val addProductIntoCartAlertDialog: AlertDialog by lazy {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.add_product_into_cart_title)
            setMessage(String.format(getString(R.string.add_product_into_cart_message), productInfo.title))
            setPositiveButton(R.string.add_product_into_cart_confirm) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this@ProductDetailsFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this@ProductDetailsFragment, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    productDetailsPageOnBackPressed()
                }
            })
        }
        storeViewModel = ViewModelProvider(this@ProductDetailsFragment, viewModelFactory).get(
            StoreViewModel::class.java)
    }

    private fun productDetailsPageOnBackPressed() {
        val destination = ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductsCatalogFragment()
        findNavController().navigate(destination)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.apply {
            product = productInfo
        }.run {
            executePendingBindings()
        }

        setupListener()
        setupLiveDataObserver()
    }

    private fun setupListener() {
        viewDataBinding.addProductIntoCartFloatingButton.setOnClickListener {
            storeViewModel.updateAndCombineListProductWithQuantityInCartAndWith(
                updatedProductWithQuantityInCart = productInfo.apply {
                    qualityInCart += 1
                })
        }
    }


    private fun setupLiveDataObserver() {
        storeViewModel.updateSuccessStatus.observe(viewLifecycleOwner, {
            if (addProductIntoCartAlertDialog.isShowing.not()) addProductIntoCartAlertDialog.show()
        })

        storeViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(viewDataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }
}