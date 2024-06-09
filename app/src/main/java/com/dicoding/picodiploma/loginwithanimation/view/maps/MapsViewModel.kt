package com.dicoding.picodiploma.loginwithanimation.view.maps

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.StoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: UserRepository) : ViewModel() {

    private val _listStory = MutableLiveData<List<StoryItem>>()
    val listStory: LiveData<List<StoryItem>> = _listStory

    fun getStory(){
        viewModelScope.launch {
            try {
                val response = repository.getStoriesLocation()
                if (response.isSuccessful) {
                    _listStory.value = response.body()?.listStory

                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }
}