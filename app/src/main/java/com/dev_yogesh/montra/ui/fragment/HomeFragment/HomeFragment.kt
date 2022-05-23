package com.dev_yogesh.montra.ui.fragment.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.model.Transaction
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.fragment.HomeFragment.adapter.RecentTransactionAdapter
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.Dialogs.selectMonthDialog
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import com.dev_yogesh.montra.utils.getCurrentMonth
import com.dev_yogesh.montra.utils.getCurrentYear
import com.dev_yogesh.montra.utils.viewState.ViewState
import dagger.hilt.android.AndroidEntryPoint
import indianRupee
import snack
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, TransactionViewModel>(),
    RecentTransactionAdapter.ItemListener
{
    private var selectedMonth = Calendar.getInstance().get(Calendar.MONTH)

    override val viewModel: TransactionViewModel by activityViewModels()
    private lateinit var mAdapter: RecentTransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
        observeTransaction()
    }

    private fun setData() = with(binding) {
        tvCurrentMonthSelected.text = getString(R.string.all)
        selectedMonth = 12
        /* )
         tvIncome.text = getString(R.string.rs_symbol).plus("5000")

         tvExpense.text = getString(R.string.rs_symbol).plus("1200")
         tvAccountBalance.text = getString(R.string.rs_symbol).plus("9400")*/

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

    private fun initListener() = with(binding) {
        mAdapter = RecentTransactionAdapter(this@HomeFragment)

        ivSpendFrequency.setOnClickListener {
            graphVisibility()
        }
        labelSpendFrequency.setOnClickListener {
            graphVisibility()
        }
        tvCurrentMonthSelected.setOnClickListener {
            selectMonthDialog(requireContext(), selectedMonth, object : DialogMonthCallback {
                override fun selectedMonth(month: String, monthInt: Int) {
                    tvCurrentMonthSelected.text = month
                    selectedMonth = monthInt
                    observeTransaction()
                }
            })
        }
        btnExpense.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExpenseFragment())
        }

        btnIncome.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToIncomeFragment())
        }

        yearChip.setOnClickListener {
            yearChip.isChecked= true
        }
        monthChip.setOnClickListener {
            monthChip.isChecked= true
        }
        weekChip.setOnClickListener {
            weekChip.isChecked= true
        }
        todayChip.setOnClickListener {
            todayChip.isChecked= true
        }

        choiceChipGroup.setOnCheckedChangeListener { group, checkedId ->
            observeTransaction()
        }

    }

    private fun graphVisibility() = with(binding) {
        if (ivGraph.isVisible) {
            ivGraph.isVisible = false
            ivSpendFrequency.rotation = 180F
        } else {
            ivGraph.isVisible = true
            ivSpendFrequency.rotation = 0F
        }
    }



    private fun onTotalTransactionLoaded(transactions: List<Transaction>) = with(binding) {
        Log.i("Filter", "transaction:: ${transactions}")
        val (totalIncome, totalExpense) = transactions.partition { it.transactionType == "Income" }
        val income = totalIncome.sumOf { it.amount }
        val expense = totalExpense.sumOf { it.amount }
        tvIncome.text = indianRupee(income)
        tvExpense.text = indianRupee(expense)
        tvAccountBalance.text = indianRupee(income - expense)

        setRecentTransactionList(transactions)
        //mAdapter.submitList(transactions)

    }
    private fun setRecentTransactionList(transactions: List<Transaction>){

        Log.i("Filter", "Transaction filter is ${(binding.monthChip.isChecked)}")
        when {
            binding.yearChip.isChecked -> {
                mAdapter.submitList(filterRecentYearList(transactions))
            }
            binding.monthChip.isChecked -> {
                Log.i("Filter", "filterRecentMonthList::::\n ${(filterRecentMonthList(transactions))}")
                mAdapter.submitList(filterRecentMonthList(transactions))
            }
            else -> {
                mAdapter.submitList(transactions)
            }





        }
        binding.rvRecentTransaction.apply {
            adapter = mAdapter
            //setHasFixedSize(true)
        }


        //


    }

    private fun filterRecentMonthList(transaction: List<Transaction>): List<Transaction> {
        val transactionList: MutableList<Transaction> = mutableListOf()
        transaction.forEach {
            if (it.month.contentEquals(getCurrentMonth())) {
                transactionList.add(it)
            }
        }
        return transactionList
    }

    private fun filterRecentYearList(transaction: List<Transaction>): List<Transaction> {
        val transactionList: MutableList<Transaction> = mutableListOf()
        transaction.forEach {
            if (it.year.contentEquals(getCurrentYear())) {
                transactionList.add(it)
            }
        }
        return transactionList
    }



    /* private fun onTransactionLoaded(list: List<Transaction>) =
         transactionAdapter.differ.submitList(list)*/

    private fun observeTransaction() = lifecycleScope.launchWhenStarted {
        viewModel.uiState.collect { uiState ->
            when (uiState) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    //showAllViews()
                    //onTransactionLoaded(uiState.transaction)
                    //filterList(uiState.transaction)
                    // onTotalTransactionLoaded(uiState.transaction)
                    onTotalTransactionLoaded(filterList(uiState.transaction))
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

    private fun filterList(transaction: List<Transaction>): List<Transaction> {
        return if (binding.tvCurrentMonthSelected.text.contentEquals(getString(R.string.all)) ||
            selectedMonth == 12
        ) {
            transaction
        } else {
            val transactionList: MutableList<Transaction> = mutableListOf()
            transaction.forEach {
                if (it.month.contentEquals(binding.tvCurrentMonthSelected.text.toString())) {
                    transactionList.add(it)
                }
            }
            return transactionList
        }
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = HomeFragment()
        //const val EXPENSE ="Expense"
        //const val INCOME ="Income"

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onTransactionClick(transaction: Transaction) {

    }


}