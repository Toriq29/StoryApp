package com.dicoding.picodiploma.loginwithanimation.view.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    fun login(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = userRepository.login(email, password)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val token = response.body()?.loginResult?.token
                    val user = token?.let { UserModel(email, it, isLogin = true) }

                    user?.let { saveSession(it) }
                    _isLogin.value = true

                } else {
                    _isLoading.value = false
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java).message
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _isLoading.value = false
                Toast.makeText(context, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}