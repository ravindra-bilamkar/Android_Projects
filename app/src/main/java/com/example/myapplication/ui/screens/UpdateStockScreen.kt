package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.StockStatus
import com.example.myapplication.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStockScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    val products by viewModel.featuredProducts.collectAsState() // Using all products from repo eventually

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Inventory") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                StockUpdateItem(
                    product = product,
                    onStatusChange = { newStatus ->
                        viewModel.updateProductStock(product.id, newStatus)
                    }
                )
            }
        }
    }
}

@Composable
fun StockUpdateItem(product: Product, onStatusChange: (StockStatus) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(product.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StockStatus.values().forEach { status ->
                    FilterChip(
                        selected = product.stockStatus == status,
                        onClick = { onStatusChange(status) },
                        label = { Text(status.name, fontSize = 11.sp) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
