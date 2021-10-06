package com.somasoma.wagglewaggle.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.somasoma.wagglewaggle.R

class MapInfoFragment : Fragment() {

    companion object {
        fun newInstance() = MapInfoFragment()
    }

    private lateinit var viewModel: MapInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}