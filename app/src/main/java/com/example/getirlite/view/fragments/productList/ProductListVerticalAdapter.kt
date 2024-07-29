package com.example.getirlite.view.fragments.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.getirlite.R
import com.example.getirlite.databinding.CellProductBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.cart.CartViewModel

class ProductListVerticalAdapter(private val model: CartViewModel, private val products: List<Product>, private val onClick: (Product) -> Unit)  : RecyclerView.Adapter<ProductListVerticalAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: CellProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(product: Product) {

            binding.iconProduct.load(product.thumbnailURL) {
                crossfade(true)
                scale(Scale.FILL)
                placeholder(R.drawable.ic_placeholder)
            }

            binding.iconProduct.clipToOutline = true
            binding.labelProductName.text = product.name
            binding.labelProductPrice.text = product.priceText
            binding.labelProductAttribute.text = product.attribute
            binding.main.setOnClickListener { onClick(product) }
            binding.stickyActions.set(model, product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder(CellProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.setData(product = products[position])

    override fun getItemCount(): Int = products.size

}