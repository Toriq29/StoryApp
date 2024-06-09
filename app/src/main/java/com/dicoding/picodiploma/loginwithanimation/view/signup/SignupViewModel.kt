package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch


class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean> = _isRegister

    fun signUp(context: Context, name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = userRepository.register(name, email, password)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val message = response.body()?.message
                    _isRegister.value = true
                } else {
                    _isLoading.value = false
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java).message
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Toast.makeText(context, "Signup failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}