package com.dicoding.picodiploma.loginwithanimation.view.add

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isUpload = MutableLiveData<Boolean>()
    val isUpload: LiveData<Boolean> = _isUpload

    fun uploadImage(context: Context,imageUri: Uri, desc: String) {
        _isLoading.value = true
        imageUri?.let { uri ->
            val imageFile = uriToFile(uri, context).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            val requestBody = desc.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            viewModelScope.launch {
                userRepository.upload(requestBody, multipartBody)
                _isLoading.value = false
                _isUpload.value = true
            }
        }
    }
}