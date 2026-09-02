package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationItem(val id: String, val title: String, val message: String, val time: String, val isRead: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onBack: () -> Unit) {
    val notifications = listOf(
        NotificationItem("1", "Flash Sale Live!", "Flat 20% off on all Cashews for next 2 hours.", "10 mins ago", false),
        NotificationItem("2", "Order Delivered", "Your order #DRY-7821 has been delivered.", "2 hours ago", true),
        NotificationItem("3", "Back in Stock", "Premium Afghan Figs are back in stock. Grab yours now!", "Yesterday", true),
        NotificationItem("4", "Special Offer", "Get a free Diwali gift box on orders above ₹2000.", "2 days ago", true)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(notifications) { notification ->
                NotificationRow(notification)
                HorizontalDivider(color = Color(0xFFF5F5F5))
            }
        }
    }
}

@Composable
fun NotificationRow(notification: NotificationItem) {
    Row(
        modifier = Modifier.fillMaxWidth().background(if (notification.isRead) Color.White else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)).padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(notification.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(notification.time, fontSize = 11.sp, color = Color.Gray)
            }
            Text(notification.message, fontSize = 13.sp, color = if (notification.isRead) Color.Gray else Color.Black)
        }
        if (!notification.isRead) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary).align(Alignment.CenterVertically))
        }
    }
}
