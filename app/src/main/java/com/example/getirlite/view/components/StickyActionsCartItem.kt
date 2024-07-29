package com.example.getirlite.view.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.getirlite.R
import com.example.getirlite.databinding.ComponentStickyActionsCartItemBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.cart.CartViewModel

class StickyActionsCartItem(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val binding = ComponentStickyActionsCartItemBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var model: CartViewModel
    private lateinit var product: Product

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.buttonAdd.setOnClickListener { onClick(product) }
        binding.buttonDelete.setOnClickListener {
            model.removeToCart(product)
            update()
        }
    }

    fun set(cartViewModel: CartViewModel, product: Product) {
        model = cartViewModel
        this.product = product
        update()
    }

    private fun onClick(product: Product) {
        model.addToCart(product)
        this.product = product
        update()
    }

    private fun update() {
        val count = model.getCount(product)
        if (count > 1) {
            binding.iconDelete.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_minus))
            binding.iconDelete.imageTintList = ColorStateList.valueOf(context.getColor(R.color.blue))
        }
        else if (count == 1) {
            binding.iconDelete.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_delete))
            binding.iconDelete.imageTintList = ColorStateList.valueOf(context.getColor(R.color.blue))
        }
        binding.labelProductCount.text = count.toString()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.buttonAdd.setOnClickListener(null)
        binding.buttonDelete.setOnClickListener(null)
    }
}