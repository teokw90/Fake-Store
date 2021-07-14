package com.kw.fakeStore.store.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kw.fakeStore.databinding.ListItemProductCardFirstBinding
import com.kw.fakeStore.databinding.ListItemProductCardSecondBinding
import com.kw.fakeStore.databinding.ListItemProductCardThirdBinding
import com.kw.fakeStore.store.source.entity.Product
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart

interface ProductItemClickListener {
    fun onSelected(productWithQuantityInCart: ProductWithQuantityInCart)
}

class ProductsCatalogAdapter(private val productItemClickListener: ProductItemClickListener): ListAdapter<ProductWithQuantityInCart, ProductsCatalogAdapter.ProductItemViewHolder>(ListOfProductDiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder = ProductItemViewHolder.from(
        parent = parent,
        viewType = viewType,
        onSelected = {
            productItemClickListener.onSelected(it)
        })

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductItemViewHolder(private val binding: ViewDataBinding, private val onSelected: (ProductWithQuantityInCart) -> Unit): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, viewType: Int, onSelected: (ProductWithQuantityInCart) -> Unit): ProductItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = when(viewType) {
                    1 -> ListItemProductCardSecondBinding.inflate(layoutInflater, parent, false)
                    2 -> ListItemProductCardThirdBinding.inflate(layoutInflater, parent, false)
                    else -> ListItemProductCardFirstBinding.inflate(layoutInflater, parent, false)
                }

                return ProductItemViewHolder(binding = binding, onSelected = onSelected)
            }
        }

        fun bind(data: ProductWithQuantityInCart) = when(binding) {
            is ListItemProductCardFirstBinding -> binding.apply {
                    onSelected = this@ProductItemViewHolder.onSelected
                productWithQuantityInCart = data
                }.run {
                    executePendingBindings()
                }
            is ListItemProductCardSecondBinding -> binding.apply {
                onSelected = this@ProductItemViewHolder.onSelected
                productWithQuantityInCart = data
            }.run {
                executePendingBindings()
            }
            is ListItemProductCardThirdBinding -> binding.apply {
                onSelected = this@ProductItemViewHolder.onSelected
                productWithQuantityInCart = data
            }.run {
                executePendingBindings()
            }
            else -> Unit
        }

    }
}

private class ListOfProductDiffCallback: DiffUtil.ItemCallback<ProductWithQuantityInCart>() {
    override fun areItemsTheSame(oldItem: ProductWithQuantityInCart, newItem: ProductWithQuantityInCart): Boolean
        = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductWithQuantityInCart, newItem: ProductWithQuantityInCart): Boolean
        = oldItem == newItem
}