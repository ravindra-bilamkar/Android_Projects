package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.repository.PaymentStatus
import com.example.myapplication.ui.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(
    amount: Double,
    viewModel: PaymentViewModel,
    onPaymentSuccess: () -> Unit,
    onBackToCart: () -> Unit
) {
    val status by viewModel.paymentStatus.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startPayment(amount)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (status) {
            is PaymentStatus.Processing -> {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Processing Payment of ₹$amount...", style = MaterialTheme.typography.titleMedium)
            }
            is PaymentStatus.Success -> {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Payment Successful!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Transaction ID: ${(status as PaymentStatus.Success).paymentId}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onPaymentSuccess, modifier = Modifier.fillMaxWidth()) {
                    Text("Continue Shopping")
                }
            }
            is PaymentStatus.Error -> {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Payment Failed", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text((status as PaymentStatus.Error).message, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onBackToCart, modifier = Modifier.fillMaxWidth()) {
                    Text("Try Again")
                }
            }
            else -> {}
        }
    }
}
