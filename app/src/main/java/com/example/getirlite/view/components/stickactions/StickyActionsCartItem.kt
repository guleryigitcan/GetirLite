package com.example.getirlite.view.components.stickactions

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.getirlite.R
import com.example.getirlite.databinding.ComponentStickyActionsCartItemBinding
import com.example.getirlite.model.extension.AssetManager
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.StickyItemInteractionListener

class StickyActionsCartItem(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val binding = ComponentStickyActionsCartItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var listener: StickyItemInteractionListener? = null
    private lateinit var product: Product

    fun set(listener: StickyItemInteractionListener, product: Product, count: Int) {
        this.listener = listener
        this.product = product
        update(count)
    }

    private fun update(count: Int) {
        binding.labelProductCount.text = count.toString()
        if (count > 1) {
            binding.iconDelete.setImageDrawable(AssetManager.drawable(R.drawable.ic_minus))
            binding.iconDelete.imageTintList = ColorStateList.valueOf(context.getColor(R.color.blue))
        } else if (count == 1) {
            binding.iconDelete.setImageDrawable(AssetManager.drawable(R.drawable.ic_delete))
            binding.iconDelete.imageTintList = ColorStateList.valueOf(context.getColor(R.color.blue))
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.buttonAdd.setOnClickListener { listener?.onAddToCart(product) }
        binding.buttonDelete.setOnClickListener { listener?.onRemoveFromCart(product) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.buttonAdd.setOnClickListener(null)
        binding.buttonDelete.setOnClickListener(null)
    }
}