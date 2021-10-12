package com.somasoma.wagglewaggle.presentation.setting

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityEditProfileBinding
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment
import com.somasoma.wagglewaggle.presentation.custom_views.SelectedInterestListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var binding: ActivityEditProfileBinding

    private val adapter = SelectedInterestListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        observe()
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

    private fun observe() {
        viewModel.showSelectInterestsDialogEvent.observe(this) { showSelectInterestsDialog() }
        viewModel.navigateToPrevPageEvent.observe(this) { navigateToPrevPage() }
        viewModel.selectedInterests.observe(this) { onSelectedInterestsChanged(it) }
        viewModel.languages.observe(this) { onLanguagesLoaded(it) }
        viewModel.countries.observe(this) { onNationsLoaded(it) }
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun showSelectInterestsDialog() {
        val editProfileSelectInterestsDialogFragment = EditProfileSelectInterestsDialogFragment.newInstance()
        editProfileSelectInterestsDialogFragment.show(
            supportFragmentManager,
            SelectInterestsDialogFragment::class.java.simpleName
        )
    }

    private fun onSelectedInterestsChanged(selectedInterests: Set<String>) {
        adapter.submitList(selectedInterests.toList())
    }

    private fun onLanguagesLoaded(languages: List<String?>) {
        val languageSpinnerListener = getLanguageSpinnerListener(languages)
        initSpinner(binding.dropdownLanguage, languages, languageSpinnerListener)
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
        val nationSpinnerListener = getNationSpinnerListener(nations)
        initSpinner(binding.dropdownNation, nations, nationSpinnerListener)
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

    private fun initSpinner(
        spinner: Spinner,
        strings: List<String?>,
        listener: AdapterView.OnItemSelectedListener
    ) {
        spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, strings)
        spinner.onItemSelectedListener = listener
    }

}