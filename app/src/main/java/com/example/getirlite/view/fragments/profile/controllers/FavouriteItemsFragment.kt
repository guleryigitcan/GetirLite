package com.example.getirlite.view.fragments.profile.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.getirlite.databinding.ControllerFavouriteItemsBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.model.product.ProductDatabase
import com.example.getirlite.view.fragments.StickyItemInteractionListener
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.productList.components.ProductListVerticalAdapter

class FavouriteItemsFragment : Fragment(), StickyItemInteractionListener{
    private var binding: ControllerFavouriteItemsBinding? = null
    private val cartViewModel: CartViewModel by activityViewModels()
    private var adapter: ProductListVerticalAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerFavouriteItemsBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val binding = binding ?: return

        val favProducts = ProductDatabase.loadFavoriteProducts()

        binding.layoutNoItems.visibility = if (favProducts.isEmpty()) View.VISIBLE else View.GONE
        binding.recycler.visibility = if (favProducts.isEmpty()) View.GONE else View.VISIBLE

        adapter = ProductListVerticalAdapter(listener = this, products = favProducts, getCount = cartViewModel::getCount) { product ->
            val direction = FavouriteItemsFragmentDirections.actionFavouriteItemsFragmentToProductDetailFragment(product.id)
            findNavController().navigate(direction)
        }

        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.recycler?.adapter = null
        adapter = null
        binding = null
    }

    override fun onAddToCart(product: Product) {
        cartViewModel.addToCart(product = product)
    }

    override fun onRemoveFromCart(product: Product) {
        cartViewModel.removeToCart(product = product)
    }
}

