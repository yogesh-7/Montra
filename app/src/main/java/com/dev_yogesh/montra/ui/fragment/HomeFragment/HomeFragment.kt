package com.dev_yogesh.montra.ui.fragment.HomeFragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.ui.MainActivity
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.Dialogs.selectMonthDialog
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import com.dev_yogesh.montra.utils.getCurrentMonth
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, TransactionViewModel>() {
    private var selectedMonth = Calendar.getInstance().get(Calendar.MONTH)

    override val viewModel: TransactionViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
    }

    private fun setData()= with(binding){
        tvCurrentMonthSelected.text = getCurrentMonth()
        tvIncome.text = getString(R.string.rs_symbol).plus("5000")

        tvExpense.text = getString(R.string.rs_symbol).plus("1200")
        tvAccountBalance.text = getString(R.string.rs_symbol).plus("9400")

        lifecycleScope.launchWhenCreated {
            viewModel.transactionFilter.collect { filter ->
               /* when (filter) {
                    "Overall" -> {
                        totalBalanceView.totalBalanceTitle.text =
                            getString(R.string.text_total_balance)
                        totalIncomeExpenseView.show()
                        incomeCardView.totalTitle.text = getString(R.string.text_total_income)
                        expenseCardView.totalTitle.text = getString(R.string.text_total_expense)
                        expenseCardView.totalIcon.setImageResource(R.drawable.ic_expense)
                    }
                    "Income" -> {
                        totalBalanceView.totalBalanceTitle.text =
                            getString(R.string.text_total_income)
                        totalIncomeExpenseView.hide()
                    }
                    "Expense" -> {
                        totalBalanceView.totalBalanceTitle.text =
                            getString(R.string.text_total_expense)
                        totalIncomeExpenseView.hide()
                    }
                }*/
                viewModel.getAllTransaction(filter)
            }
        }

    }
    private fun initListener()= with(binding){
        ivSpendFrequency.setOnClickListener {
            graphVisibility()
        }
        labelSpendFrequency.setOnClickListener {
            graphVisibility()
        }
        tvCurrentMonthSelected.setOnClickListener {
            selectMonthDialog(requireContext(),selectedMonth,object : DialogMonthCallback{
                override fun selectedMonth(month: String, monthInt: Int) {
                    tvCurrentMonthSelected.text= month
                    selectedMonth = monthInt
                }
            })
        }
        btnExpense.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExpenseFragment())
        }

        btnIncome.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToIncomeFragment())
        }

    }

    private fun graphVisibility()=with(binding){
        if(ivGraph.isVisible){
            ivGraph.isVisible=false
            ivSpendFrequency.rotation=180F
        }else{
            ivGraph.isVisible=true
            ivSpendFrequency.rotation=0F
        }
    }




    companion object {
        val TAG = this::class.toString()
        fun getInstance() = HomeFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentHomeBinding.inflate(inflater, container, false)
}