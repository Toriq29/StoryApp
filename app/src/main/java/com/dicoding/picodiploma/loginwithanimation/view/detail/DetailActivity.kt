package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PHOTO_URL = "photo_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra(NAME)
        binding.tvDescription.text = intent.getStringExtra(DESCRIPTION)
        Glide.with(this)
            .load(intent.getStringExtra(PHOTO_URL))
            .into(binding.storyImage)

    }
}