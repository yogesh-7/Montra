package com.dev_yogesh.montra.ui.fragment.TransactionFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.databinding.FragmentProfileBinding
import com.dev_yogesh.montra.databinding.FragmentTransactionBinding
import com.dev_yogesh.montra.model.Transaction
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.fragment.HomeFragment.HomeFragment
import com.dev_yogesh.montra.ui.fragment.HomeFragment.adapter.RecentTransactionAdapter
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.getCurrentMonth
import com.dev_yogesh.montra.utils.viewState.ViewState
import dagger.hilt.android.AndroidEntryPoint
import snack


@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding, TransactionViewModel>() , RecentTransactionAdapter.ItemListener {


    override val viewModel: TransactionViewModel by activityViewModels()

    private lateinit var mAdapter: RecentTransactionAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentTransactionBinding.inflate(inflater, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
        observeTransaction()
    }

    private fun setData()= with(binding){

        lifecycleScope.launchWhenCreated {
            viewModel.transactionFilter.collect { filter ->
                viewModel.getAllTransaction(filter)
            }
        }
    }
    private fun initListener()= with(binding){
        mAdapter = RecentTransactionAdapter(this@TransactionFragment)
        rvRecentTransaction.apply {
            adapter = mAdapter
        }
    }

    private fun observeTransaction() = lifecycleScope.launchWhenStarted {
        viewModel.uiState.collect { uiState ->
            when (uiState) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    mAdapter.submitList(uiState.transaction)
                }
                is ViewState.Error -> {
                    binding.root.snack(
                        string = R.string.text_error
                    )
                }
                is ViewState.Empty -> {
                    //hideAllViews()
                }
            }
        }
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = TransactionFragment()
    }

    override fun onTransactionClick(transaction: Transaction) {

    }
}