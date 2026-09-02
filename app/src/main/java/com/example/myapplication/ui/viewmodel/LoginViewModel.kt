package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.EnterMobile)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _mobileNumber = MutableStateFlow("")
    val mobileNumber: StateFlow<String> = _mobileNumber

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onMobileNumberChange(newNumber: String) {
        if (newNumber.length <= 10) {
            _mobileNumber.value = newNumber
        }
    }

    fun onOtpChange(newOtp: String) {
        if (newOtp.length <= 6) {
            _otp.value = newOtp
        }
    }

    fun requestOtp() {
        if (_mobileNumber.value.length != 10) {
            _error.value = "Please enter a valid 10-digit mobile number"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            if (repository.requestOtp(_mobileNumber.value)) {
                _uiState.value = LoginUiState.EnterOtp
            } else {
                _error.value = "Failed to send OTP. Try again."
            }
            _isLoading.value = false
        }
    }

    fun verifyOtp() {
        if (_otp.value.length != 6) {
            _error.value = "Please enter a 6-digit OTP"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            if (repository.verifyOtp(_mobileNumber.value, _otp.value)) {
                _uiState.value = LoginUiState.Authenticated
            } else {
                _error.value = "Invalid OTP. Please try again (Hint: 123456)"
            }
            _isLoading.value = false
        }
    }

    fun backToMobile() {
        _uiState.value = LoginUiState.EnterMobile
        _otp.value = ""
        _error.value = null
    }
}

sealed class LoginUiState {
    object EnterMobile : LoginUiState()
    object EnterOtp : LoginUiState()
    object Authenticated : LoginUiState()
}
