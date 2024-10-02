package com.example.getirlite.view.fragments.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirlite.model.product.Product
import com.example.getirlite.model.services.APIClient
import com.example.getirlite.model.services.Interface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(): ViewModel() {
    val verticalProducts: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val suggestedProducts: MutableLiveData<List<Product>> = MutableLiveData(listOf())

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            try {
                val verticalProductResponse = APIClient.client?.create(Interface::class.java)?.verticalProducts()
                val horizontalProductResponse = APIClient.client?.create(Interface::class.java)?.suggestedProducts()
                verticalProductResponse?.let {
                    verticalProducts.value = it[0].products
                }
                horizontalProductResponse?.let {
                    suggestedProducts.value = it[0].products
                }
            } catch (_: Exception) {}

        }
    }

    fun findProductById(id: String): Product? {
        return verticalProducts.value?.firstOrNull { it.id == id } ?: suggestedProducts.value?.first { it.id == id }
    }
}