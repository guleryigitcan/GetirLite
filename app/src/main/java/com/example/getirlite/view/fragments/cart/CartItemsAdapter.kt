package com.example.getirlite.view.fragments.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.getirlite.databinding.CellCartProductBinding
import com.example.getirlite.model.product.Product

class CartItemsAdapter (private val model: CartViewModel, private val products: List<Product>, private val onClick: (Product) -> Unit)  : RecyclerView.Adapter<CartItemsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: CellCartProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(product: Product) {
            val imageURL = (product.thumbnailURL ?: product.imageURL) ?: product.squareThumbnailURL
            binding.iconProduct.load(imageURL) {
                crossfade(true)
                scale(Scale.FILL)
            }

            binding.iconProduct.clipToOutline = true

            binding.labelProductName.text = product.name
            binding.labelProductPrice.text = product.priceText
            binding.labelProductAttribute.text = product.attribute

            binding.main.setOnClickListener { onClick(product) }
            binding.stickyActions.set(model, product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder(CellCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.setData(product = products[position])

    override fun getItemCount(): Int = products.size

}