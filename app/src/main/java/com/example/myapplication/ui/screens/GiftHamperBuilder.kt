package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.repository.GroceryRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftHamperBuilder(
    onBack: () -> Unit,
    onHamperComplete: (List<Product>, String) -> Unit
) {
    var selectedBoxSize by remember { mutableStateOf("Medium") }
    val boxSizes = listOf("Small (4 items)", "Medium (6 items)", "Large (10 items)")
    val maxItems = when(selectedBoxSize) {
        "Small (4 items)" -> 4
        "Medium (6 items)" -> 6
        "Large (10 items)" -> 10
        else -> 6
    }
    
    val availableProducts by GroceryRepository.products.collectAsState()
    val selectedItems = remember { mutableStateListOf<Product>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Build Your Gift Hamper", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Button(
                    onClick = { onHamperComplete(selectedItems.toList(), selectedBoxSize) },
                    enabled = selectedItems.isNotEmpty(),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ADD HAMPER TO CART (₹${selectedItems.sumOf { it.price } + 200})")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Box Size Selector
            Column(modifier = Modifier.padding(16.dp)) {
                Text("1. Select Box Size", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    boxSizes.forEach { size ->
                        FilterChip(
                            selected = selectedBoxSize == size,
                            onClick = { 
                                selectedBoxSize = size
                                // Items are cleared if they exceed new max
                                while (selectedItems.size > maxItems) {
                                    selectedItems.removeAt(selectedItems.size - 1)
                                }
                            },
                            label = { Text(size, fontSize = 12.sp) }
                        )
                    }
                }
            }

            Divider()

            // Item Selection
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("2. Add Items (${selectedItems.size}/$maxItems)", fontWeight = FontWeight.Bold)
                    if (selectedItems.size >= maxItems) {
                        Text("Box Full!", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(availableProducts) { product ->
                        val isSelected = selectedItems.contains(product)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color(0xFFF9F9F9))
                                .clickable(enabled = isSelected || selectedItems.size < maxItems) {
                                    if (isSelected) selectedItems.remove(product)
                                    else selectedItems.add(product)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(product.name.take(1))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, fontWeight = FontWeight.Medium)
                                Text("₹${product.price}", color = Color.Gray, fontSize = 12.sp)
                            }
                            Icon(
                                if (isSelected) Icons.Default.CheckCircle else Icons.Default.Add,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
