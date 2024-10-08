package com.example.getirlite.view.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.getirlite.databinding.ControllerCartBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.StickyItemInteractionListener
import com.example.getirlite.view.fragments.cart.components.CartItemsAdapter
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.example.getirlite.view.fragments.productList.components.SuggestedProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


class CartFragment: Fragment(), StickyItemInteractionListener {
    private var binding: ControllerCartBinding? = null
    private val productListViewModel: ProductListViewModel by activityViewModels()
    private val model: CartViewModel by activityViewModels()
    private var cartItemsAdapter: CartItemsAdapter? = null
    private var suggestedProductsAdapter: SuggestedProductsAdapter? = null

    private lateinit var cartObserver: Observer<List<Product>>
    private lateinit var suggestedProductsObserver: Observer<List<Product>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerCartBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindCartObserver()
    }

    private fun bindView() {
        val binding = binding ?: return

        cartObserver = Observer { products ->
            products.let {
                if (it.isNotEmpty()) {
                    cartItemsAdapter = CartItemsAdapter(listener = this, products = it, getCount = model::getCount) { product ->
                        val direction = CartFragmentDirections.actionCartFragmentToProductDetailFragment(product.id)
                        findNavController().navigate(direction)
                    }
                    binding.recyclerCartItems.adapter = cartItemsAdapter
                } else {
                    val direction = CartFragmentDirections.actionCartFragmentToProductListFragment()
                    findNavController().navigate(direction)
                }
                val decimalFormat = DecimalFormat("0.00")
                val priceText = "₺${decimalFormat.format(model.totalAmount)}"
                binding.labelTotalPrice.text = priceText
            }
        }
        model.cartItems.observe(viewLifecycleOwner, cartObserver)

        suggestedProductsObserver = Observer { products ->
            products.let {
                if (it.isNotEmpty()) {
                    suggestedProductsAdapter = SuggestedProductsAdapter(listener = this, products = it, getCount = model::getCount) { product ->
                        val direction = CartFragmentDirections.actionCartFragmentToProductDetailFragment(product.id)
                        findNavController().navigate(direction)
                    }
                    binding.recyclerSuggestedItems.adapter = suggestedProductsAdapter
                }
            }
        }

        productListViewModel.suggestedProducts.observe(viewLifecycleOwner, suggestedProductsObserver)

        binding.buttonCheckout.setOnClickListener {
            val message = "Siparişiniz Aldık! Toplam Ücret: ₺${"%.2f".format(model.totalAmount)}"
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()

            model.clearCart()
        }
    }

    private fun bindCartObserver() {
        cartObserver = Observer {
            suggestedProductsAdapter?.notifyDataSetChanged()
        }

        model.cartItems.observe(viewLifecycleOwner, cartObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.cartItems.removeObserver(cartObserver)
        productListViewModel.suggestedProducts.removeObserver(suggestedProductsObserver)
        binding?.buttonCheckout?.setOnClickListener(null)
        binding?.recyclerCartItems?.adapter = null
        binding?.recyclerSuggestedItems?.adapter = null
        cartItemsAdapter = null
        suggestedProductsAdapter = null
        binding = null
    }

    override fun onAddToCart(product: Product) {
        model.addToCart(product)
    }

    override fun onRemoveFromCart(product: Product) {
        model.removeToCart(product)
    }
}