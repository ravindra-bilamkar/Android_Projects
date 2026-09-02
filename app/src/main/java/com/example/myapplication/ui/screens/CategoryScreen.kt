package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Product
import com.example.myapplication.ui.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryName: String,
    products: List<Product>,
    onAddToCart: (Product) -> Unit,
    onBack: () -> Unit
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("Popularity") }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            // Quick Filters
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = { showFilterSheet = true },
                        label = { Text("Sort: $selectedSort") },
                        trailingIcon = { Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
                items(listOf("100g", "250g", "500g", "1kg")) { weight ->
                    FilterChip(
                        selected = false,
                        onClick = { },
                        label = { Text(weight) }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductItem(product = product, onAddToCart = onAddToCart)
                }
            }
        }

        if (showFilterSheet) {
            ModalBottomSheet(
                onDismissRequest = { showFilterSheet = false },
                sheetState = sheetState
            ) {
                FilterContent(
                    selectedSort = selectedSort,
                    onSortSelected = { 
                        selectedSort = it
                        showFilterSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun FilterContent(selectedSort: String, onSortSelected: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Text("Sort By", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        val sortOptions = listOf("Popularity", "Price: Low to High", "Price: High to Low", "Discount")
        sortOptions.forEach { option ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedSort == option, onClick = { onSortSelected(option) })
                Text(option, modifier = Modifier.padding(start = 8.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Origin", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Indian", "Californian", "Afghani").forEach { origin ->
                FilterChip(selected = false, onClick = {}, label = { Text(origin) })
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /* Apply */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Apply Filters")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
