package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(onBack: () -> Unit) {
    val steps = listOf(
        "Order Placed" to "10:30 AM",
        "Packed" to "10:35 AM",
        "Out for Delivery" to "10:40 AM",
        "Delivered" to "Pending"
    )
    val currentStep = 2

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Track Order #12345", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isTablet) {
            Row(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp)) {
                Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                    Text("Order Status", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    steps.forEachIndexed { index, step ->
                        TimelineItem(
                            title = step.first,
                            subtitle = step.second,
                            isCompleted = index < currentStep,
                            isCurrent = index == currentStep,
                            isLast = index == steps.size - 1
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f).padding(start = 24.dp)) {
                    Text("Delivery Details", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    DeliveryPartnerCard()
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(), 
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Delivery Address", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("123, 10th Main, Indiranagar, Bangalore", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                steps.forEachIndexed { index, step ->
                    TimelineItem(
                        title = step.first,
                        subtitle = step.second,
                        isCompleted = index < currentStep,
                        isCurrent = index == currentStep,
                        isLast = index == steps.size - 1
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                DeliveryPartnerCard()
                
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(), 
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Delivery Address", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("123, 10th Main, Indiranagar, Bangalore", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun DeliveryPartnerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape), 
                contentAlignment = Alignment.Center
            ) {
                Text("🚚", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Delivery Partner", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Rahul is on his way!", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun TimelineItem(
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    isCurrent: Boolean,
    isLast: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        if (isCompleted || isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        Icons.Default.CheckCircle, 
                        contentDescription = null, 
                        tint = MaterialTheme.colorScheme.onPrimary, 
                        modifier = Modifier.size(18.dp)
                    )
                } else if (isCurrent) {
                    Box(modifier = Modifier.size(10.dp).background(MaterialTheme.colorScheme.onPrimary, CircleShape))
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Text(
                text = title,
                style = if (isCurrent) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                color = if (isCompleted || isCurrent) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
            Text(
                text = subtitle, 
                style = MaterialTheme.typography.bodySmall, 
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
