package com.example.myapplication.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MixIngredient(val name: String, val pricePer100g: Double, val type: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMixScreen(onBack: () -> Unit, onAddToCart: (String, Double) -> Unit) {
    val bases = listOf(
        MixIngredient("Roasted Peanuts", 40.0, "Base"),
        MixIngredient("Corn Flakes", 30.0, "Base"),
        MixIngredient("Puffed Rice", 25.0, "Base")
    )
    val nuts = listOf(
        MixIngredient("Almonds", 120.0, "Nuts"),
        MixIngredient("Cashews", 140.0, "Nuts"),
        MixIngredient("Walnuts", 180.0, "Nuts"),
        MixIngredient("Pistachios", 200.0, "Nuts")
    )
    val sweeteners = listOf(
        MixIngredient("Raisins", 60.0, "Sweeteners"),
        MixIngredient("Dried Figs", 100.0, "Sweeteners"),
        MixIngredient("Dates", 80.0, "Sweeteners")
    )

    var selectedBase by remember { mutableStateOf<MixIngredient?>(null) }
    val selectedNuts = remember { mutableStateListOf<MixIngredient>() }
    val selectedSweeteners = remember { mutableStateListOf<MixIngredient>() }
    var weightInGrams by remember { mutableFloatStateOf(250f) }

    val totalPrice = remember(selectedBase, selectedNuts.size, selectedSweeteners.size, weightInGrams) {
        val basePrice = selectedBase?.pricePer100g ?: 0.0
        val nutsPrice = selectedNuts.sumOf { it.pricePer100g }
        val sweetPrice = selectedSweeteners.sumOf { it.pricePer100g }
        val ingredientCount = (if (selectedBase != null) 1 else 0) + selectedNuts.size + selectedSweeteners.size
        if (ingredientCount == 0) 0.0 else ((basePrice + nutsPrice + sweetPrice) / ingredientCount) * (weightInGrams / 100.0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Build Your Own Mix", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Price", fontSize = 12.sp, color = Color.Gray)
                        Text("₹${String.format("%.2f", totalPrice)}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(
                        onClick = { onAddToCart("Custom Mix (${weightInGrams.toInt()}g)", totalPrice) },
                        enabled = selectedBase != null,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("ADD TO CART")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            item {
                Text("1. Choose a Base", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(bases) { item ->
                IngredientChip(item, selectedBase == item) { selectedBase = item }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("2. Add Nuts (Optional)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(nuts) { item ->
                IngredientChip(item, selectedNuts.contains(item)) {
                    if (selectedNuts.contains(item)) selectedNuts.remove(item) else selectedNuts.add(item)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("3. Add Sweeteners (Optional)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(sweeteners) { item ->
                IngredientChip(item, selectedSweeteners.contains(item)) {
                    if (selectedSweeteners.contains(item)) selectedSweeteners.remove(item) else selectedSweeteners.add(item)
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Select Weight: ${weightInGrams.toInt()}g", fontWeight = FontWeight.Bold)
                Slider(
                    value = weightInGrams,
                    onValueChange = { weightInGrams = it },
                    valueRange = 100f..1000f,
                    steps = 8,
                    colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun IngredientChip(ingredient: MixIngredient, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(ingredient.name, modifier = Modifier.weight(1f), fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
            Text("₹${ingredient.pricePer100g}/100g", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
            if (isSelected) Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        }
    }
}
