package com.somasoma.wagglewaggle.domain.home

import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.dp2Px
import com.somasoma.wagglewaggle.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        private const val BACKGROUND_SEMICIRCLE_HEIGHT_IN_DP = 295
        private const val BACKGROUND_SEMICIRCLE_HORIZONTAL_MARGIN_IN_DP = 24
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        val screenWidth = getScreenWidth()
        viewModel.backgroundSemicircleHeight = calculateBackgroundSemicircleHeight(screenWidth)
        viewModel.backgroundSemicircleRadius = calculateBackgroundSemicircleRadius(screenWidth)
    }

    private fun getScreenWidth() = if (android.os.Build.VERSION.SDK_INT < 30) {
        windowManager.defaultDisplay.width
    } else {
        val metrics = windowManager.currentWindowMetrics
        val windowInsets = metrics.windowInsets
        val insets =
            windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
        val insetsWidth = insets.left + insets.right
        val bounds = metrics.bounds
        val width = bounds.width() - insetsWidth
        width
    }

    private fun calculateBackgroundSemicircleRadius(screenWidth: Int) = (screenWidth / 2) + dp2Px(
        this,
        BACKGROUND_SEMICIRCLE_HORIZONTAL_MARGIN_IN_DP.toFloat()
    )

    private fun calculateBackgroundSemicircleHeight(screenWidth: Int) =
        dp2Px(this, BACKGROUND_SEMICIRCLE_HEIGHT_IN_DP.toFloat())
}