package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Store(val name: String, val address: String, val distance: String, val timing: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreLocatorScreen(onBack: () -> Unit) {
    val stores = listOf(
        Store("DryFruit Hub - Indiranagar", "123, 100ft Road, Indiranagar, Bangalore", "1.2 km away", "Open 9 AM - 10 PM"),
        Store("DryFruit Hub - Koramangala", "45, 80ft Road, 4th Block, Koramangala", "4.5 km away", "Open 10 AM - 9 PM"),
        Store("DryFruit Hub - HSR Layout", " Sector 2, HSR Layout, Bangalore", "6.1 km away", "Open 9 AM - 10 PM"),
        Store("Dark Store - Whitefield", "Kundanahalli Main Road, Bangalore", "12 km away", "24/7 Delivery Only")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Outlets", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
                    Text("Interactive Map Mockup", color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Nearby Outlets", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(stores) { store ->
                StoreItem(store)
            }
        }
    }
}

@Composable
fun StoreItem(store: Store) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(store.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(store.address, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(store.distance, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(store.timing, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Button(onClick = { /* View on Map */ }, shape = RoundedCornerShape(8.dp)) {
                Text("VIEW", fontSize = 12.sp)
            }
        }
    }
}
