package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Category
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.StockStatus
import com.example.myapplication.data.repository.GroceryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repository: GroceryRepository = GroceryRepository) : ViewModel() {
    val categories: StateFlow<List<Category>> = repository.categories

    val featuredProducts: StateFlow<List<Product>> = repository.products
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions = _suggestions.asStateFlow()

    private val _trendingSearches = MutableStateFlow(listOf("Diwali Hampers", "Roasted Almonds", "Gift Boxes", "Keto Mix"))
    val trendingSearches = _trendingSearches.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(listOf("Almonds", "Cashews", "Walnuts"))
    val recentSearches = _recentSearches.asStateFlow()

    fun updateSearchQuery(query: String) {
        if (query.isEmpty()) {
            _suggestions.value = emptyList()
            return
        }
        val allProductNames = repository.products.value.map { it.name }
        _suggestions.value = allProductNames.filter { it.contains(query, ignoreCase = true) }.take(5)
    }

    fun addRecentSearch(query: String) {
        if (query.isNotBlank() && !_recentSearches.value.contains(query)) {
            _recentSearches.value = (listOf(query) + _recentSearches.value).take(5)
        }
    }

    fun getProductsByCategory(categoryId: String): List<Product> {
        return repository.getProductsByCategory(categoryId)
    }

    fun getProductById(productId: String): Product? {
        return repository.getProductById(productId)
    }

    fun addProduct(name: String, price: Double, categoryName: String) {
        repository.addProduct(name, price, categoryName)
    }

    fun updateProductStock(productId: String, newStatus: StockStatus) {
        repository.updateProductStock(productId, newStatus)
    }
}
