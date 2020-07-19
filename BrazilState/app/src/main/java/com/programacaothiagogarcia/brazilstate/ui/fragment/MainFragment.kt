package com.programacaothiagogarcia.brazilstate.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.programacaothiagogarcia.brazilstate.R
import com.programacaothiagogarcia.brazilstate.databinding.MainFragmentBinding
import com.programacaothiagogarcia.brazilstate.ui.adapter.StatesAdapter
import com.programacaothiagogarcia.brazilstate.ui.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var mAdapter: StatesAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.apply {
            lifecycleOwner = this@MainFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareAdapter()
        prepareView()
        prepareViewModel()


    }

    private fun prepareAdapter() {
        mAdapter = context?.let { StatesAdapter(it) }!!
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.statesList?.observe(viewLifecycleOwner, Observer {
            mAdapter.stateList = it
        })
        viewModel.filterList?.observe(viewLifecycleOwner, Observer {
            prepareChipView(it) { tag, isChecked ->
                viewModel.onFilterChanged(tag, isChecked)
            }
        })
        binding.viewmodel = viewModel
    }

    private fun prepareChipView(
        itensFilter: List<String>, callback: (String, Boolean)
        -> Unit
    ) {
        val chipGroup = binding.regionList
        val inflator = LayoutInflater.from(chipGroup.context)
        val children = itensFilter.map { regionName ->
            val chip = inflator.inflate(R.layout.region, chipGroup, false) as Chip
            chip.text = regionName
            chip.tag = regionName
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                callback(buttonView.tag as String, isChecked)
            }
            chip
        }
        chipGroup.removeAllViews()
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }

    private fun prepareView() {
        binding.rvState.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = context?.let { GridLayoutManager(it, 3) }
        }
    }

}