package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(private val repository: CartRepository = CartRepository) : ViewModel() {
    val cartState: StateFlow<Map<Product, Int>> = repository.cartState

    fun addProduct(product: Product) {
        repository.addProduct(product)
    }

    fun removeProduct(product: Product) {
        repository.removeProduct(product)
    }

    fun clearCart() {
        repository.clearCart()
    }
}
