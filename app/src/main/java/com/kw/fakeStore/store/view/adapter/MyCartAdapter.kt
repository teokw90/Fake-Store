package com.kw.fakeStore.store.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kw.fakeStore.databinding.ListItemProductInCartBinding
import com.kw.fakeStore.store.uiModel.ProductWithQuantityInCart

interface CartItemClickListener {
    fun onDeleteButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart)
    fun onDecreaseProductQualityButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart)
    fun onIncreaseProductQualityButtonClicked(productWithQuantityInCart: ProductWithQuantityInCart)
}
class CartItemAdapter(private val cartItemClickListener: CartItemClickListener): ListAdapter<ProductWithQuantityInCart, CartItemAdapter.CartItemViewHolder>(ListOfProductInTheCartDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder = CartItemViewHolder.from(
        parent = parent,
        onDeleteButtonClicked = {
            cartItemClickListener.onDeleteButtonClicked(it)
        },
        onDecreaseProductQualityButtonClicked = {
            cartItemClickListener.onDecreaseProductQualityButtonClicked(it)
        },
        onIncreaseProductQualityButtonClicked = {
            cartItemClickListener.onIncreaseProductQualityButtonClicked(it)
        }
    )

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) = holder.bind(getItem(position))

    class CartItemViewHolder(private val binding: ListItemProductInCartBinding,
                             private val onDeleteButtonClicked: (ProductWithQuantityInCart) -> Unit,
                             private val onDecreaseProductQualityButtonClicked: (ProductWithQuantityInCart) -> Unit,
                             private val onIncreaseProductQualityButtonClicked: (ProductWithQuantityInCart) -> Unit): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(
                parent: ViewGroup,
                onDeleteButtonClicked: (ProductWithQuantityInCart) -> Unit,
                onDecreaseProductQualityButtonClicked: (ProductWithQuantityInCart) -> Unit,
                onIncreaseProductQualityButtonClicked: (ProductWithQuantityInCart) -> Unit
            ): CartItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductInCartBinding.inflate(layoutInflater, parent, false)

                return CartItemViewHolder(
                    binding = binding,
                    onDeleteButtonClicked = onDeleteButtonClicked,
                    onDecreaseProductQualityButtonClicked = onDecreaseProductQualityButtonClicked,
                    onIncreaseProductQualityButtonClicked = onIncreaseProductQualityButtonClicked
                )
            }
        }

        fun bind(data: ProductWithQuantityInCart) = binding.apply {
            onDeleteButtonClicked = this@CartItemViewHolder.onDeleteButtonClicked
            onDecreaseProductQualityButtonClicked =
                this@CartItemViewHolder.onDecreaseProductQualityButtonClicked
            onIncreaseProductQualityButtonClicked =
                this@CartItemViewHolder.onIncreaseProductQualityButtonClicked
            productWithQuantityInCart = data
        }.run {
            executePendingBindings()
        }
    }
}

private class ListOfProductInTheCartDiffCallback: DiffUtil.ItemCallback<ProductWithQuantityInCart>() {
    override fun areItemsTheSame(oldItem: ProductWithQuantityInCart, newItem: ProductWithQuantityInCart): Boolean
            = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductWithQuantityInCart, newItem: ProductWithQuantityInCart): Boolean
            = oldItem == newItem
}