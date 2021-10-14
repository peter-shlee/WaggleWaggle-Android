package com.somasoma.wagglewaggle.presentation.setting

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityEditProfileBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment
import com.somasoma.wagglewaggle.presentation.custom_views.SelectedInterestListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {

    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var binding: ActivityEditProfileBinding
    private val adapter = SelectedInterestListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        observe()
        repeatOnStart { collect() }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listInterest.adapter = adapter
        setLayoutManager()
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listInterest.layoutManager = layoutManager
    }

    private suspend fun collect() {
        viewModel.eventFlow.collect { handleEvent(it) }
    }

    private fun observe() {
        viewModel.selectedInterests.observe(this) { onSelectedInterestsChanged(it) }
        viewModel.languages.observe(this) { onLanguagesLoaded(it) }
        viewModel.countries.observe(this) { onNationsLoaded(it) }
        viewModel.loadedCountry.observe(this) { onSelectedCountryLoaded(it) }
        viewModel.loadedLanguage.observe(this) { onSelectedLanguageLoaded(it) }
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun showSelectInterestsDialog() {
        val editProfileSelectInterestsDialogFragment =
            EditProfileSelectInterestsDialogFragment.newInstance()
        editProfileSelectInterestsDialogFragment.show(
            supportFragmentManager,
            SelectInterestsDialogFragment::class.java.simpleName
        )
    }

    private fun onSelectedInterestsChanged(selectedInterests: Set<String>) {
        adapter.submitList(selectedInterests.toList())
    }

    private fun onLanguagesLoaded(languages: List<String?>) {
        binding.dropdownLanguage.adapter = ArrayAdapter(this, R.layout.spinner_item, languages)
        binding.dropdownLanguage.onItemSelectedListener = getLanguageSpinnerListener(languages)
        val loadedLanguage = viewModel.loadedLanguage.value ?: return
        onSelectedLanguageLoaded(loadedLanguage)
    }

    private fun getLanguageSpinnerListener(languages: List<String?>) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectedLanguage = languages[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectedLanguage = languages[0]
            }
        }

    private fun onNationsLoaded(nations: List<String?>) {
        binding.dropdownNation.adapter = ArrayAdapter(this, R.layout.spinner_item, nations)
        binding.dropdownNation.onItemSelectedListener = getNationSpinnerListener(nations)
        val loadedCountry = viewModel.loadedCountry.value ?: return
        onSelectedCountryLoaded(loadedCountry)
    }

    private fun getNationSpinnerListener(nations: List<String?>) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectedCountry = nations[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.selectedCountry = nations[0]
            }
        }

    private fun onSelectedCountryLoaded(country: String) {
        binding.dropdownNation.setSelection(
            viewModel.countries.value?.indexOf(country) ?: 0
        )
    }

    private fun onSelectedLanguageLoaded(language: String) {
        binding.dropdownLanguage.setSelection(
            viewModel.languages.value?.indexOf(language) ?: 0
        )
    }

    private fun handleEvent(event: EditProfileViewModel.Event) = when (event) {
        EditProfileViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
        EditProfileViewModel.Event.ShowSelectInterestsDialog -> showSelectInterestsDialog()
    }
}