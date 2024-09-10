package com.example.getirlite.view.fragments.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import com.example.getirlite.databinding.ControllerProductDetailBinding
import com.example.getirlite.model.product.Product
import com.example.getirlite.view.fragments.StickyItemInteractionListener
import com.example.getirlite.view.fragments.cart.CartFragmentDirections
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.example.getirlite.view.fragments.productList.SuggestedProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment: Fragment(), StickyItemInteractionListener {
    private var binding: ControllerProductDetailBinding? = null
    private val model: ProductListViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private var suggestedProductsAdapter: SuggestedProductsAdapter? = null
    private lateinit var suggestedProductsObserver: Observer<List<Product>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerProductDetailBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        val productId = arguments?.getString("productId") ?: return

        val product = model.findProductById(productId) ?: return
        binding.iconProduct.load(product.imageURL){
            crossfade(true)
            scale(Scale.FILL)
        }
        binding.labelProductName.text = product.name
        binding.labelProductAttribute.text = product.attribute
        binding.labelProductPrice.text = product.priceText

        binding.buttonAddToCart.set(cartViewModel, product)

        suggestedProductsObserver = Observer { products ->
            products.let {
                if (it.isNotEmpty()) {
                    suggestedProductsAdapter = SuggestedProductsAdapter(listener = this, products = it, getCount = cartViewModel::getCount) { product ->
                        val direction = CartFragmentDirections.actionCartFragmentToProductDetailFragment(product.id)
                        findNavController().navigate(direction)
                    }
                    binding.recyclerSuggestedItems.adapter = suggestedProductsAdapter
                }
            }
        }

        model.suggestedProducts.observe(viewLifecycleOwner, suggestedProductsObserver)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        model.suggestedProducts.removeObserver(suggestedProductsObserver)
        binding?.recyclerSuggestedItems?.adapter = null
        suggestedProductsAdapter = null
        binding = null
    }

    override fun onAddToCart(product: Product) {
        cartViewModel.addToCart(product = product)
        suggestedProductsAdapter?.notifyDataSetChanged()
    }

    override fun onRemoveFromCart(product: Product) {
        cartViewModel.removeToCart(product = product)
        suggestedProductsAdapter?.notifyDataSetChanged()
    }
}