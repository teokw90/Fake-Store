package com.kw.fakeStore.store.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.kw.fakeStore.R
import com.kw.fakeStore.databinding.FragmentMyCartBinding
import com.kw.fakeStore.store.viewModel.StoreViewModel
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import com.kw.fakeStore.store.view.adapter.CartItemAdapter
import com.kw.fakeStore.store.view.adapter.CartItemClickListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class MyCartFragment: Fragment(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var storeViewModel: StoreViewModel
    private lateinit var viewDataBinding: FragmentMyCartBinding
    private var listOfProductWithQuantityInCart: List<ProductWithQuantityInCart> = emptyList()
    private val cartItemAdapter: CartItemAdapter by lazy {
        CartItemAdapter(object: CartItemClickListener {
            override fun onDeleteButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart) {
                listOfProductWithQuantityInCart.toMutableList().apply {
                    remove(productWithQuantityInCart)
                }.run {
                    listOfProductWithQuantityInCart = this.toList()
                    cartItemAdapter.submitList(this.toList())
                }
            }

            override fun onDecreaseProductQualityButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart) {
                val updatedQuantityInCart = productWithQuantityInCart.qualityInCart - 1
                updateListOfProductWithQuantityInCartWith(updatedQuantityInCart, productWithQuantityInCart)
            }

            override fun onIncreaseProductQualityButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart) {
                val updatedQuantityInCart = productWithQuantityInCart.qualityInCart + 1
                updateListOfProductWithQuantityInCartWith(updatedQuantityInCart, productWithQuantityInCart)
            }
        })
    }

    private fun updateListOfProductWithQuantityInCartWith(updatedQuantityInCart: Int, productWithQuantityInCart: ProductWithQuantityInCart) {
        listOfProductWithQuantityInCart.toMutableList().apply {
            if (updatedQuantityInCart == 0 ) {
                remove(productWithQuantityInCart)
            } else {
                set(indexOf(productWithQuantityInCart), productWithQuantityInCart.copy().apply {
                    qualityInCart = updatedQuantityInCart
                })
            }
        }.run {
            listOfProductWithQuantityInCart = this.toList()
            cartItemAdapter.submitList(this.toList())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this@MyCartFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeViewModel = ViewModelProvider(this@MyCartFragment, viewModelFactory).get(StoreViewModel::class.java)
        requireActivity().apply {
            onBackPressedDispatcher.addCallback(this@MyCartFragment, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    myCartPageOnBackPressed()
                }
            })
        }
    }

    private fun myCartPageOnBackPressed() {
        val destination = MyCartFragmentDirections.actionMyCartFragmentToProductsCatalogFragment()
        findNavController().navigate(destination)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentMyCartBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.productCatalogRecyclerView.apply {
            adapter = cartItemAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        setupListener()
        setupLiveDataObserver()
    }

    private fun setupListener() = with(viewDataBinding) {
        checkOutFloatingButton.setOnClickListener {
            storeViewModel.updateWith(listOfProductWithQuantityInCart)
        }
    }


    private fun setupLiveDataObserver() {
        storeViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(viewDataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })

        storeViewModel.listOfProductWithQuantityInCart.observe(viewLifecycleOwner, {
            listOfProductWithQuantityInCart = it
            cartItemAdapter.submitList(it)
        })

        storeViewModel.updateSuccessStatus.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), getString(R.string.my_cart_update_successful),Toast.LENGTH_LONG).show()
        })
    }

    override fun onStart() {
        super.onStart()
        storeViewModel.getListOfProductInCart()
    }
}