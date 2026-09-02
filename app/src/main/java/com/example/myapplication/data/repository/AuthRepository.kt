package com.example.myapplication.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepository {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    suspend fun requestOtp(mobileNumber: String): Boolean {
        // Mock API call to send OTP
        delay(1000)
        return mobileNumber.length == 10
    }

    suspend fun verifyOtp(mobileNumber: String, otp: String): Boolean {
        // Mock API call to verify OTP
        delay(1000)
        return if (otp == "123456") {
            _isLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}
