package com.dev_yogesh.montra.ui.fragment.BudgetFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentBudgetBinding
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.databinding.FragmentProfileBinding
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.fragment.ProfileFragment.ProfileFragment
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BudgetFragment :  BaseFragment<FragmentBudgetBinding, TransactionViewModel>() {


    override val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
    }

    private fun setData()= with(binding){

    }
    private fun initListener()= with(binding){

    }

    companion object {
        val TAG = this::class.toString()
        fun getInstance() = BudgetFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBudgetBinding.inflate(inflater, container, false)
}
