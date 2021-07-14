package com.kw.fakeStore.store.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kw.fakeStore.R
import com.kw.fakeStore.databinding.FragmentProductsCatalogBinding
import com.kw.fakeStore.store.view.adapter.ProductsCatalogAdapter
import com.kw.fakeStore.store.view.adapter.ProductItemClickListener
import com.kw.fakeStore.store.view.adapter.decoration.ProductGridItemDecoration
import com.kw.fakeStore.store.viewModel.StoreViewModel
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductsCatalogFragment: Fragment(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var storeViewModel: StoreViewModel
    private lateinit var viewDataBinding: FragmentProductsCatalogBinding
    private val gridLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if(position % 3 == 2) 2 else 1
            }
        }
    }
    private val productsCatalogAdapter: ProductsCatalogAdapter by lazy {
        ProductsCatalogAdapter(productItemClickListener = object: ProductItemClickListener {
                override fun onSelected(productWithQuantityInCart: ProductWithQuantityInCart) {
                    navigateToProductDetailsPageWith(productWithQuantityInCart)
                }
        })
    }

    private fun navigateToProductDetailsPageWith(productWithQuantityInCart: ProductWithQuantityInCart) {
        val destination = ProductsCatalogFragmentDirections.actionProductsCatalogFragmentToProductDetailsFragment(productInfo = productWithQuantityInCart)
        findNavController().navigate(destination)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this@ProductsCatalogFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeViewModel = ViewModelProvider(this@ProductsCatalogFragment, viewModelFactory).get(StoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentProductsCatalogBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val largePadding = resources.getDimensionPixelSize(R.dimen.product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.product_grid_spacing_small)
        viewDataBinding.productCatalogRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = productsCatalogAdapter
            addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }

        setupListener()
        setupLiveDataObserver()
    }

    private fun setupListener() {
        viewDataBinding.viewCartFloatingButton.setOnClickListener {
            navigateToMyCartPage()
        }
    }

    private fun navigateToMyCartPage() {
        val destination = ProductsCatalogFragmentDirections.actionProductsCatalogFragmentToMyCartFragment()
        findNavController().navigate(destination)
    }

    private fun setupLiveDataObserver() {
        storeViewModel.listOfProductWithQuantityInCart.observe(viewLifecycleOwner, {
            productsCatalogAdapter.submitList(it)
        })

        storeViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                Snackbar.make(viewDataBinding.root, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        storeViewModel.getListOfProduct()
    }
}