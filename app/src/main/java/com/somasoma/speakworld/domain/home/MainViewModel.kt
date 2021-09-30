package com.somasoma.speakworld.domain.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0
}