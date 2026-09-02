package com.example.myapplication.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import com.example.myapplication.data.model.Category
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.ProductVariant
import com.example.myapplication.data.model.StockStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object GroceryRepository {
    private val _categories = MutableStateFlow(listOf(
        Category("1", "Premium Dry Fruits", Icons.Default.Star),
        Category("2", "Freshly Ground Flours", Icons.Default.Info),
        Category("3", "Healthy Seeds", Icons.Default.Star),
        Category("4", "Diwali Specials", Icons.Default.Favorite),
        Category("5", "Wedding Hampers", Icons.Default.DateRange),
        Category("6", "Keto Friendly", Icons.Default.Face),
        Category("7", "Vegan Selection", Icons.Default.Favorite)
    ))
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private fun getStandardVariants(basePrice: Double): List<ProductVariant> {
        return listOf(
            ProductVariant("100gm", basePrice),
            ProductVariant("200gm", basePrice * 1.9),
            ProductVariant("250gm", basePrice * 2.4),
            ProductVariant("500gm", basePrice * 4.5),
            ProductVariant("750gm", basePrice * 6.5),
            ProductVariant("1kg", basePrice * 8.5)
        )
    }

    private val _products = MutableStateFlow(listOf(
        Product(
            id = "1",
            name = "Almonds",
            price = 250.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1508817628294-5a453fa0b8fb?q=80&w=400&auto=format&fit=crop",
            description = "Handpicked premium almonds, perfectly roasted and crunchy.",
            origin = "California, USA",
            variants = getStandardVariants(250.0),
            rating = 4.8,
            reviewCount = 2450
        ),
        Product(
            id = "2",
            name = "Cashews",
            price = 300.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1536622513470-388f8d5f3083?q=80&w=400&auto=format&fit=crop",
            description = "Large, white, and crispy cashews from the best farms.",
            origin = "Mangalore, India",
            variants = getStandardVariants(300.0),
            rating = 4.7,
            reviewCount = 1820,
            stockStatus = StockStatus.LowStock
        ),
        Product(
            id = "3",
            name = "Peanuts",
            price = 100.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1534119394579-248e1bf97bc5?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(100.0),
            rating = 4.5,
            reviewCount = 560
        ),
        Product(
            id = "4",
            name = "Pistachios",
            price = 350.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1551021461-1250269550e2?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(350.0),
            rating = 4.9,
            reviewCount = 920
        ),
        Product(
            id = "5",
            name = "Walnuts",
            price = 450.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1582294101416-2481077651a5?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(450.0),
            rating = 4.6,
            reviewCount = 1100
        ),
        Product(
            id = "6",
            name = "Hazelnuts",
            price = 500.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1541819661-8012f6057a6e?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(500.0),
            rating = 4.7,
            reviewCount = 890
        ),
        Product(
            id = "7",
            name = "Pecans",
            price = 550.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1596541578332-9a6744033b00?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(550.0),
            rating = 4.6,
            reviewCount = 450
        ),
        Product(
            id = "8",
            name = "Pine Nuts",
            price = 900.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1596541578332-9a6744033b00?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(900.0),
            rating = 4.8,
            reviewCount = 120
        ),
        Product(
            id = "9",
            name = "Figs",
            price = 400.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1610444583737-1234b6b66e01?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(400.0),
            rating = 4.4,
            reviewCount = 850,
            stockStatus = StockStatus.LowStock
        ),
        Product(
            id = "10",
            name = "Prunes",
            price = 380.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1596541578332-9a6744033b00?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(380.0),
            rating = 4.5,
            reviewCount = 230
        ),
        Product(
            id = "11",
            name = "Apricots",
            price = 420.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1501430654243-c93f8679fccd?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(420.0),
            rating = 4.7,
            reviewCount = 430
        ),
        Product(
            id = "12",
            name = "Cherries",
            price = 600.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1528821128474-27f963b062bf?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(600.0),
            rating = 4.8,
            reviewCount = 670
        ),
        Product(
            id = "13",
            name = "Berries",
            price = 550.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1522204523234-8729aa6e3d5f?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(550.0),
            rating = 4.7,
            reviewCount = 540
        ),
        Product(
            id = "14",
            name = "Mangoes",
            price = 450.0,
            category = "Premium Dry Fruits",
            imageUrl = "https://images.unsplash.com/photo-1601004890684-d8cbf643f5f2?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(450.0),
            rating = 4.6,
            reviewCount = 910
        ),
        Product(
            id = "15",
            name = "Sunflower Seeds",
            price = 120.0,
            category = "Healthy Seeds",
            imageUrl = "https://images.unsplash.com/photo-1506543730435-e2c1d4553a84?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(120.0),
            rating = 4.5,
            reviewCount = 310
        ),
        Product(
            id = "16",
            name = "Flax Seeds",
            price = 100.0,
            category = "Healthy Seeds",
            imageUrl = "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(100.0),
            rating = 4.6,
            reviewCount = 280
        ),
        Product(
            id = "17",
            name = "Chia Seeds",
            price = 200.0,
            category = "Healthy Seeds",
            imageUrl = "https://images.unsplash.com/photo-1511211119777-630245a4980a?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(200.0),
            rating = 4.7,
            reviewCount = 1200
        ),
        Product(
            id = "18",
            name = "Pumpkin Seeds",
            price = 250.0,
            category = "Healthy Seeds",
            imageUrl = "https://images.unsplash.com/photo-1509048191080-d2984920f39e?q=80&w=400&auto=format&fit=crop",
            variants = getStandardVariants(250.0),
            rating = 4.8,
            reviewCount = 850
        ),
        Product(
            id = "19",
            name = "Sharbati Wheat",
            price = 60.0,
            category = "Freshly Ground Flours",
            imageUrl = "https://images.unsplash.com/photo-1627483262112-039e9a0a0f16?q=80&w=400&auto=format&fit=crop",
            variants = listOf(
                ProductVariant("1kg", 60.0),
                ProductVariant("2kg", 115.0),
                ProductVariant("5kg", 280.0)
            ),
            rating = 4.8,
            reviewCount = 5200,
            stockStatus = StockStatus.InStock
        ),
        Product(
            id = "20",
            name = "Jowar Flour",
            price = 80.0,
            category = "Freshly Ground Flours",
            imageUrl = "https://images.unsplash.com/photo-1509440159596-0249088772ff?q=80&w=400&auto=format&fit=crop",
            variants = listOf(
                ProductVariant("1kg", 80.0),
                ProductVariant("2kg", 150.0),
                ProductVariant("5kg", 360.0)
            ),
            rating = 4.7,
            reviewCount = 980,
            stockStatus = StockStatus.InStock
        ),
        Product(
            id = "21",
            name = "Bajra Flour",
            price = 70.0,
            category = "Freshly Ground Flours",
            imageUrl = "https://images.unsplash.com/photo-1615485290382-441e4d019cb5?q=80&w=400&auto=format&fit=crop",
            variants = listOf(
                ProductVariant("1kg", 70.0),
                ProductVariant("2kg", 135.0),
                ProductVariant("5kg", 320.0)
            ),
            rating = 4.6,
            reviewCount = 420,
            stockStatus = StockStatus.InStock
        ),
        Product(
            id = "22",
            name = "Multigrain Flour",
            price = 95.0,
            category = "Freshly Ground Flours",
            imageUrl = "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?q=80&w=400&auto=format&fit=crop",
            variants = listOf(
                ProductVariant("1kg", 95.0),
                ProductVariant("2kg", 185.0),
                ProductVariant("5kg", 450.0)
            ),
            rating = 4.9,
            reviewCount = 1500,
            stockStatus = StockStatus.InStock
        )
    ))
    
    private var isOffline = false
    private val _cachedProducts = MutableStateFlow<List<Product>>(emptyList())
    
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        _cachedProducts.value = _products.value
    }

    fun toggleOfflineMode(offline: Boolean) {
        isOffline = offline
    }

    fun getCatalog(): StateFlow<List<Product>> {
        return if (isOffline) _cachedProducts.asStateFlow() else _products.asStateFlow()
    }

    fun addProduct(name: String, price: Double, categoryName: String) {
        val newId = (_products.value.size + 1).toString()
        val newProduct = Product(newId, name, price, categoryName, "")
        _products.update { it + newProduct }
    }

    fun updateProductStock(productId: String, newStatus: StockStatus) {
        _products.update { currentProducts ->
            currentProducts.map { product ->
                if (product.id == productId) {
                    product.copy(stockStatus = newStatus)
                } else {
                    product
                }
            }
        }
    }

    fun getProductsByCategory(categoryId: String): List<Product> {
        val categoryName = _categories.value.find { it.id == categoryId }?.name ?: return emptyList()
        return _products.value.filter { it.category == categoryName }
    }

    fun getProductById(productId: String): Product? {
        return _products.value.find { it.id == productId }
    }
}
