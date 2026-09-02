package com.example.myapplication.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentRepository {
    private val _paymentStatus = MutableStateFlow<PaymentStatus>(PaymentStatus.Idle)
    val paymentStatus: StateFlow<PaymentStatus> = _paymentStatus

    suspend fun initiatePayment(amount: Double) {
        _paymentStatus.value = PaymentStatus.Processing
        // Mock payment processing delay
        delay(2000)
        // Simulate success
        _paymentStatus.value = PaymentStatus.Success("PAYID_${System.currentTimeMillis()}")
    }

    fun resetStatus() {
        _paymentStatus.value = PaymentStatus.Idle
    }
}

sealed class PaymentStatus {
    object Idle : PaymentStatus()
    object Processing : PaymentStatus()
    data class Success(val paymentId: String) : PaymentStatus()
    data class Error(val message: String) : PaymentStatus()
}
