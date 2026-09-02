package com.example.myapplication.data.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
    val description: String = "",
    val origin: String = "",
    val nutritionalInfo: Map<String, String> = emptyMap(),
    val ingredients: String = "",
    val shelfLife: String = "",
    val variants: List<ProductVariant> = emptyList(),
    val rating: Double = 4.5,
    val reviewCount: Int = 120,
    val stockStatus: StockStatus = StockStatus.InStock
)

enum class StockStatus {
    InStock, LowStock, OutOfStock
}

data class ProductVariant(
    val weight: String,
    val price: Double
)
