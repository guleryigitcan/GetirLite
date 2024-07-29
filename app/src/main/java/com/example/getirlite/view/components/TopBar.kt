package com.example.getirlite.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.getirlite.R
import com.example.getirlite.databinding.ComponentTopBarBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.cart.CartViewModel
import java.text.DecimalFormat

class TopBar(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val binding = ComponentTopBarBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var navController: NavController
    private lateinit var model: CartViewModel
    private lateinit var cartObserver: Observer<List<Product>>

    fun set(navController: NavController, cartViewModel: CartViewModel) {
        this.navController = navController
        this.model = cartViewModel
        bindView()
    }

    private fun bindView() {
        binding.buttonCart.setOnClickListener {
            if (!model.isCartEmpty) navController.navigate(R.id.action_global_cartFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.labelControllerName.text = destination.label

            binding.buttonCart.visibility = if (destination.id == R.id.cartFragment) GONE else VISIBLE
            binding.iconDeleteCart.visibility = if (destination.id == R.id.cartFragment) VISIBLE else GONE
        }

        binding.iconExit.setOnClickListener {
            navController.popBackStack()
        }

        binding.iconDeleteCart.setOnClickListener { model.clearCart() }

        cartObserver = Observer {
            val decimalFormat = DecimalFormat("0.00")
            val priceText = "₺${decimalFormat.format(model.totalAmount)}"
            binding.labelTotalPrice.text = priceText
        }

        model.cartItems.observe(context as LifecycleOwner, cartObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.buttonCart.setOnClickListener(null)
        binding.iconExit.setOnClickListener(null)
        binding.iconDeleteCart.setOnClickListener(null)
        model.cartItems.removeObserver(cartObserver)
    }
}