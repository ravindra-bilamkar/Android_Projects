package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SubscriptionItem(val id: String, val name: String, val frequency: String, val nextDelivery: String, val price: Double, val status: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(onBack: () -> Unit) {
    val activeSubscriptions = listOf(
        SubscriptionItem("1", "Monthly Nut Box (Premium)", "Every 30 Days", "15 Sep 2026", 1200.0, "Active"),
        SubscriptionItem("2", "Keto Seeds Mix", "Every 15 Days", "05 Sep 2026", 450.0, "Paused")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subscribe & Save", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE8E8)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Save up to 15% on subscriptions!", fontWeight = FontWeight.Bold, color = Color.Red)
                            Text("Free shipping on all recurring orders.", fontSize = 12.sp)
                        }
                    }
                }
                Text("Your Subscriptions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(activeSubscriptions) { sub ->
                SubscriptionCard(sub)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SubscriptionCard(sub: SubscriptionItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(sub.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    sub.status,
                    color = if (sub.status == "Active") Color(0xFF00B259) else Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Frequency: ${sub.frequency}", fontSize = 14.sp, color = Color.Gray)
            Text("Next Delivery: ${sub.nextDelivery}", fontSize = 14.sp, color = Color.Gray)
            Text("Price: ₹${sub.price}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { /* Modify */ }) { Text("MODIFY") }
                Button(
                    onClick = { /* Pause/Resume */ },
                    colors = ButtonDefaults.buttonColors(containerColor = if (sub.status == "Active") Color.LightGray else MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (sub.status == "Active") "PAUSE" else "RESUME", color = if (sub.status == "Active") Color.Black else Color.White)
                }
            }
        }
    }
}
