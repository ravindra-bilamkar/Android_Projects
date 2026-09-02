package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportCenter(onBack: () -> Unit) {
    var showTicketDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<String?>(null) }
    val mockOrders = listOf("#DRY-7821", "#DRY-7815", "#DRY-7790")

    val faqs = listOf(
        "Where is my order?" to "You can track your order in the 'Orders' section.",
        "How do I cancel my order?" to "Orders can be cancelled before they are packed.",
        "Return Policy" to "We have a no-questions-asked return policy for damaged items.",
        "Payment Issues" to "If your payment failed but money was deducted, it will be refunded within 3-5 days."
    )

    if (showTicketDialog) {
        AlertDialog(
            onDismissRequest = { showTicketDialog = false },
            title = { Text("Raise a Ticket") },
            text = {
                Column {
                    Text("Select Order:")
                    mockOrders.forEach { order ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { selectedOrder = order }.padding(8.dp)) {
                            RadioButton(selected = selectedOrder == order, onClick = { selectedOrder = order })
                            Text(order)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Mock upload */ }, modifier = Modifier.fillMaxWidth()) {
                        Text("UPLOAD PHOTO (MOCK)")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showTicketDialog = false }) { Text("SUBMIT") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support Center", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Button(
                    onClick = { showTicketDialog = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("RAISE A TICKET", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { /* Mock chat */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("CHAT WITH US", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(24.dp)
                ) {
                    Column {
                        Text("How can we help you today?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Search for topics or browse FAQs", fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Common FAQs", fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
            }

            items(faqs) { faq ->
                FAQItem(faq.first)
            }
        }
    }
}

@Composable
fun FAQItem(question: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Show answer */ },
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(question, fontWeight = FontWeight.Medium)
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF5F5F5))
}
