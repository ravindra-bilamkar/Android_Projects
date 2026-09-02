package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PaymentRepository
import com.example.myapplication.data.repository.PaymentStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val repository: PaymentRepository = PaymentRepository()) : ViewModel() {

    val paymentStatus: StateFlow<PaymentStatus> = repository.paymentStatus

    fun startPayment(amount: Double) {
        viewModelScope.launch {
            repository.initiatePayment(amount)
        }
    }

    fun reset() {
        repository.resetStatus()
    }
}
