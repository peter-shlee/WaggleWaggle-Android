package com.somasoma.speakworld.domain.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.ActivityFollowerBinding

class FollowerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFollowerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follower)
    }
}