package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.model.Category
import com.example.myapplication.data.model.Product
import com.example.myapplication.ui.components.LocationSelector
import com.example.myapplication.ui.components.ProductItem
import com.example.myapplication.ui.components.StickyCartBar
import com.example.myapplication.ui.viewmodel.CartViewModel
import com.example.myapplication.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    isAdmin: Boolean = false,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onGiftHamperClick: () -> Unit,
    onViewCart: () -> Unit,
    onAddItemClick: () -> Unit = {},
    onUpdateStockClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    locationPermissionGranted: Boolean = true
) {
    val categories by viewModel.categories.collectAsState()
    val featuredProducts by viewModel.featuredProducts.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val trendingSearches by viewModel.trendingSearches.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val columns = if (configuration.screenWidthDp < 600) 2 else 4

    val occasionCategories = categories.filter { it.id in listOf("4", "5") }
    val dietCategories = categories.filter { it.id in listOf("6", "7") }

    Scaffold(
        floatingActionButton = {
            if (isAdmin) {
                Column(horizontalAlignment = Alignment.End) {
                    FloatingActionButton(
                        onClick = onUpdateStockClick,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.update_stock))
                    }
                    FloatingActionButton(
                        onClick = onAddItemClick,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(bottom = 80.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_item))
                    }
                }
            }
        },
        bottomBar = {
            StickyCartBar(viewModel = cartViewModel, onViewCart = onViewCart)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                LocationSelector(
                    locationPermissionGranted = locationPermissionGranted,
                    onLocationClick = {}
                )
                
                // Search Bar
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { 
                        searchQuery = it
                        viewModel.updateSearchQuery(it)
                    },
                    onSearch = { 
                        viewModel.addRecentSearch(it)
                        isSearchActive = false 
                    },
                    active = isSearchActive,
                    onActiveChange = { isSearchActive = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isSearchActive) 0.dp else 16.dp, vertical = if (isSearchActive) 0.dp else 8.dp),
                    placeholder = { 
                        Text(
                            stringResource(R.string.search_placeholder), 
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    leadingIcon = { 
                        Icon(
                            Icons.Default.Search, 
                            contentDescription = null, 
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        inputFieldColors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    ),
                    shape = if (isSearchActive) RoundedCornerShape(0.dp) else RoundedCornerShape(12.dp)
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            stringResource(R.string.recent_searches),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        recentSearches.forEach { search ->
                            ListItem(
                                headlineContent = { Text(search, style = MaterialTheme.typography.bodyLarge) },
                                leadingContent = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                                modifier = Modifier.clickable { 
                                    searchQuery = search
                                    viewModel.updateSearchQuery(search)
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                            )
                        }
                    } else {
                        suggestions.forEach { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion, style = MaterialTheme.typography.bodyLarge) },
                                leadingContent = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                                modifier = Modifier.clickable { 
                                    searchQuery = suggestion
                                    viewModel.addRecentSearch(suggestion)
                                    isSearchActive = false
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                            )
                        }
                    }
                }

                // Hero Banner
                Surface(
                    onClick = { /* Navigate to offers */ },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    HeroBanner()
                }
                
                // Custom Mix Banner
                val customMixLabel = stringResource(R.string.custom_mix)
                CustomMixBanner(onClick = { onCategoryClick(Category("custom", customMixLabel, Icons.Default.Refresh)) })

                // Gift Hamper Promotion
                GiftHamperBanner(onClick = onGiftHamperClick)

                Spacer(modifier = Modifier.height(16.dp))

                // Shop by Occasion
                CategorySection(title = stringResource(R.string.shop_by_occasion), categories = occasionCategories, onCategoryClick = onCategoryClick)
                
                Spacer(modifier = Modifier.height(16.dp))

                // Shop by Diet
                CategorySection(title = stringResource(R.string.shop_by_diet), categories = dietCategories, onCategoryClick = onCategoryClick)

                Spacer(modifier = Modifier.height(24.dp))

                // All Categories
                Text(
                    text = stringResource(R.string.browse_categories),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = false,
                            onClick = { onCategoryClick(category) },
                            label = { Text(category.name, style = MaterialTheme.typography.labelMedium) },
                            shape = RoundedCornerShape(20.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
                
                Text(
                    text = stringResource(R.string.recommended_for_you),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            val chunkedProducts = featuredProducts.chunked(columns)
            items(chunkedProducts) { rowProducts ->
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    rowProducts.forEach { product ->
                        ProductItem(
                            product = product,
                            onAddToCart = onAddToCart,
                            onProductClick = onProductClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(columns - rowProducts.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySection(title: String, categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            items(categories) { category ->
                Card(
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier.size(width = 140.dp, height = 80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.BottomStart) {
                        Text(category.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun GiftHamperBanner(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D1B22), // Darker Pink variant for dark mode
            contentColor = Color(0xFFFDEEF4)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.customize_gift), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFFFF4081))
                Text(stringResource(R.string.pick_box_goodies), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFFFF4081))
        }
    }
}

@Composable
fun CustomMixBanner(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B2D1C), // Darker Green variant for dark mode
            contentColor = Color(0xFFE8F5E9)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.build_your_own_mix), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                Text(stringResource(R.string.mix_ingredients), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.Refresh, contentDescription = null, tint = Color(0xFF4CAF50))
        }
    }
}

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0066FF), // AppPrimaryBlue
                        Color(0xFF4D94FF)  // Lighter blue variant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.mins_delivery),
                color = MaterialTheme.colorScheme.tertiary, // Use Zepto Green
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = stringResource(R.string.premium_dry_fruits_banner),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White, // Always white on blue banner
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun HomeScreenPhonePreview() {
    val viewModel = HomeViewModel()
    val cartViewModel = CartViewModel()
    HomeScreen(
        viewModel = viewModel,
        cartViewModel = cartViewModel,
        onAddToCart = {},
        onProductClick = {},
        onCategoryClick = {},
        onGiftHamperClick = {},
        onViewCart = {}
    )
}
