package com.somasoma.wagglewaggle.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.model.Avatars
import com.somasoma.wagglewaggle.databinding.ActivityHomeBinding
import com.somasoma.wagglewaggle.databinding.CharacterPagerItemBinding
import com.somasoma.wagglewaggle.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var viewPagerAdapter: CharacterPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserInfo()

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewPagerAdapter =
            CharacterPagerAdapter(viewModel.avatars.value ?: Avatars(arrayListOf()))
        binding.viewpagerSelectCharacter.adapter = viewPagerAdapter

        binding.viewpagerSelectCharacter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val index = binding.viewpagerSelectCharacter.currentItem
                viewModel.avatars.value?.run {
                    viewModel.selectedCharacter = list?.get(index).toString()
                }

                Timber.d(viewModel.selectedCharacter)
            }
        })

        observe()
    }

    private fun observe() {
        viewModel.navigateToSettingActivityEvent.observe(this) { navigateToSettingActivity() }
        viewModel.avatars.observe(this) { onCharactersLoaded() }
    }

    private fun onCharactersLoaded() {
        viewModel.avatars.value?.let {
            viewPagerAdapter.avatars = it
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun navigateToSettingActivity() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
}

class CharacterPagerAdapter(var avatars: Avatars) :
    RecyclerView.Adapter<CharacterViewHolder>() {
    override fun getItemCount(): Int {
        return avatars.list?.size ?: 0
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.character = avatars.list?.get(position).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView =
            CharacterPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemView)
    }
}

class CharacterViewHolder(val binding: CharacterPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root)