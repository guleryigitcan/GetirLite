package com.example.getirlite.view.fragments.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.getirlite.databinding.ControllerProductListBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.StickyItemInteractionListener
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.productList.components.ProductListVerticalAdapter
import com.example.getirlite.view.fragments.productList.components.SuggestedProductsAdapter
import dagger.hilt.android.AndroidEntryPoint


class ProductListFragment: Fragment(), StickyItemInteractionListener {
    private var binding: ControllerProductListBinding? = null
    private val model: ProductListViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private var suggestedProductsAdapter: SuggestedProductsAdapter? = null
    private var verticalAdapter: ProductListVerticalAdapter? = null

    private lateinit var suggestedProductsObserver: Observer<List<Product>>
    private lateinit var verticalProductsObserver: Observer<List<Product>>
    private lateinit var cartObserver: Observer<List<Product>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerProductListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindCartObserver()
    }

    private fun bindView() {
        val binding = binding ?: return
        binding.recyclerVertical.suppressLayout(true)

        suggestedProductsObserver = Observer { productResponse ->
            productResponse.let {
                if (it.isNotEmpty()) {
                    suggestedProductsAdapter = SuggestedProductsAdapter(listener = this, products = it, getCount = cartViewModel::getCount) { product ->
                        val direction = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product.id)
                        findNavController().navigate(direction)
                    }
                    binding.recyclerSuggested.adapter = suggestedProductsAdapter
                }
            }
        }

       verticalProductsObserver = Observer{ productResponse ->
           productResponse.let {
               if (it.isNotEmpty()) {
                   verticalAdapter = ProductListVerticalAdapter(listener = this, products = it, getCount = cartViewModel::getCount) { product ->
                       val direction = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product.id)
                       findNavController().navigate(direction)
                   }
                   binding.recyclerVertical.adapter = verticalAdapter
               }
           }
        }

        model.suggestedProducts.observe(viewLifecycleOwner, suggestedProductsObserver)
        model.verticalProducts.observe(viewLifecycleOwner, verticalProductsObserver)

    }


    private fun bindCartObserver() {
        cartObserver = Observer {
            suggestedProductsAdapter?.notifyDataSetChanged()
            verticalAdapter?.notifyDataSetChanged()
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner, cartObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.suggestedProducts.removeObserver(suggestedProductsObserver)
        model.verticalProducts.removeObserver(verticalProductsObserver)
        binding?.recyclerSuggested?.adapter = null
        binding?.recyclerVertical?.adapter = null
        suggestedProductsAdapter = null
        verticalAdapter = null
        binding = null
    }

    override fun onAddToCart(product: Product) {
        cartViewModel.addToCart(product = product)
    }

    override fun onRemoveFromCart(product: Product) {
        cartViewModel.removeToCart(product = product)
    }
}