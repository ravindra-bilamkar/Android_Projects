package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CartRepository {
    private val _cartState = MutableStateFlow<Map<Product, Int>>(emptyMap())
    val cartState: StateFlow<Map<Product, Int>> = _cartState.asStateFlow()

    fun addProduct(product: Product) {
        _cartState.update { currentCart ->
            val currentCount = currentCart[product] ?: 0
            currentCart + (product to currentCount + 1)
        }
    }

    fun removeProduct(product: Product) {
        _cartState.update { currentCart ->
            val currentCount = currentCart[product] ?: 0
            if (currentCount <= 1) {
                currentCart - product
            } else {
                currentCart + (product to currentCount - 1)
            }
        }
    }
    
    fun clearCart() {
        _cartState.value = emptyMap()
    }
}
