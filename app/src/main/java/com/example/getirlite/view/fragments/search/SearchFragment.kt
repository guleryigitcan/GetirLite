package com.example.getirlite.view.fragments.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.getirlite.databinding.ControllerSearchBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.StickyItemInteractionListener
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.productList.ProductListFragmentDirections
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.example.getirlite.view.fragments.productList.components.ProductListVerticalAdapter
import com.example.getirlite.view.fragments.search.components.PopularSearchAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class SearchFragment: Fragment(), StickyItemInteractionListener {
    private var binding: ControllerSearchBinding? = null
    private val model: ProductListViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private var productAdapter: ProductListVerticalAdapter? = null
    private var textWatcher: TextWatcher? = null
    private var productsObserver: Observer<List<Product>>? = null
    private var cartObserver: Observer<List<Product>>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindCartObserver()
    }

    private fun bindView() {
        val binding = binding ?: return

        binding.recyclerPopularSearch.adapter = PopularSearchAdapter { selectedKeyword ->
            binding.search.setText(selectedKeyword)
            search(selectedKeyword.lowercase(Locale.getDefault()))
        }

        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) = search(s.toString().lowercase(Locale.getDefault()))
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }

        binding.search.addTextChangedListener(textWatcher)

        productsObserver = Observer{ productResponse ->
            productResponse.let {
                if (it.isNotEmpty()) {
                    productAdapter = ProductListVerticalAdapter(listener = this, products = it, getCount = cartViewModel::getCount) { product ->
                        val direction = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id)
                        findNavController().navigate(direction)
                    }
                    binding.recyclerSearchItems.adapter = productAdapter
                }
            }
        }
        productsObserver?.let { model.verticalProducts.observe(viewLifecycleOwner, it) }
    }

    private fun search(searchQuery: String) {
        val binding = binding ?: return

        if (searchQuery.isEmpty()) return

        CoroutineScope(Dispatchers.Main).launch {
            val filteredList = withContext(Dispatchers.Default) {
                val list = model.verticalProducts.value ?: listOf()
                list.filter { it.name.lowercase().contains(searchQuery) }
            }


            binding.recyclerPopularSearch.visibility = if (filteredList.isNotEmpty()) View.GONE else View.VISIBLE
            binding.recyclerSearchItems.visibility = if (filteredList.isNotEmpty()) View.VISIBLE else View.GONE

            productAdapter?.update(filteredList)
        }
    }

    private fun bindCartObserver() {
        cartObserver = Observer {
            productAdapter?.notifyDataSetChanged()
        }

        cartObserver?.let { cartViewModel.cartItems.observe(viewLifecycleOwner, it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartObserver?.let { cartViewModel.cartItems.removeObserver(it) }
        productsObserver?.let { model.verticalProducts.removeObserver(it) }
        binding?.search?.removeTextChangedListener(textWatcher)
        binding?.recyclerPopularSearch?.adapter = null
        textWatcher = null
        productAdapter = null
        productsObserver = null
        cartObserver = null
        binding = null
    }

    override fun onAddToCart(product: Product) {
        cartViewModel.addToCart(product = product)
    }

    override fun onRemoveFromCart(product: Product) {
        cartViewModel.removeToCart(product = product)
    }
}