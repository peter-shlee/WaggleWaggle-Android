package com.somasoma.wagglewaggle.presentation.auth.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySignUpBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment
import com.somasoma.wagglewaggle.presentation.custom_views.SelectedInterestListAdapter
import com.somasoma.wagglewaggle.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    private val adapter = SelectedInterestListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        collect()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initInterestListRecyclerView()
    }

    private fun initInterestListRecyclerView() {
        binding.listInterest.adapter = adapter
        setLayoutManager()
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listInterest.layoutManager = layoutManager
    }

    private fun collect() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
        repeatOnStart { viewModel.selectedInterests.collect { onSelectedInterestsChanged(it) } }
        repeatOnStart { viewModel.languages.collect { onLanguagesLoaded(it) } }
        repeatOnStart { viewModel.countries.collect { onNationsLoaded(it) } }
    }

    private fun handleEvent(event: SignUpViewModel.Event) = when(event) {
        SignUpViewModel.Event.NavigateToMainPage -> navigateToMain()
        SignUpViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
        SignUpViewModel.Event.ShowSelectInterestsDialog -> showSelectInterestsDialog()
    }

    private fun showSelectInterestsDialog() {
        val signUpSelectInterestsDialogFragment = SignUpSelectInterestsDialogFragment.newInstance()
        signUpSelectInterestsDialogFragment.show(
            supportFragmentManager,
            SelectInterestsDialogFragment::class.java.simpleName
        )
    }

    private fun onSelectedInterestsChanged(selectedInterests: Set<String>) {
        adapter.submitList(selectedInterests.toList())
    }

    private fun onLanguagesLoaded(languages: List<String?>) {
        Timber.d(languages.toString())
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
        Timber.d(nations.toString())
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

    private fun navigateToPrevPage() {
        finish()
    }

    private fun navigateToMain() {
        val navigateIntent = Intent(this, MainActivity::class.java)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(navigateIntent)
    }

}