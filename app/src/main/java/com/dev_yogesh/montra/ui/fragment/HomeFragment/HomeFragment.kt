package com.dev_yogesh.montra.ui.fragment.HomeFragment

import android.R.bool
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
import com.dev_yogesh.montra.utils.Constants.Income
import com.dev_yogesh.montra.utils.Dialogs.selectMonthDialog
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import com.dev_yogesh.montra.utils.getCurrentDate
import com.dev_yogesh.montra.utils.getCurrentMonth
import com.dev_yogesh.montra.utils.getCurrentWeekStartEndDate
import com.dev_yogesh.montra.utils.getCurrentYear
import com.dev_yogesh.montra.utils.isThisIsInCurrentWeek
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
                    if(  selectedMonth ==12 ){
                        yearChip.isChecked= true
                    }else{
                        monthChip.isChecked = true
                    }
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

        choiceChipGroup.setOnCheckedChangeListener { _, _ ->
            observeTransaction()
        }

        tvSeeAllTransaction.setOnClickListener {
         findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransactionFragment())
        }
        rvRecentTransaction.apply {
            adapter = mAdapter
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

    private fun setRecentTransactionList(transactions: List<Transaction>) = with(binding){

        Log.i("Filter", "Transaction filter is ${(monthChip.isChecked)}")
        when {
            yearChip.isChecked -> {
                setListAndCalculateData(filterRecentYearList(transactions))
                tvCurrentMonthSelected.text = "All"
                selectedMonth = 12
            }
            monthChip.isChecked -> {
                Log.i("Filter", "filterRecentMonthList::::\n ${(filterRecentMonthList(transactions))}")
                setListAndCalculateData(filterRecentMonthList(transactions))
            }
            todayChip.isChecked -> {
                Log.i("Filter", "filterRecentMonthList::::\n ${(filterRecentCurrentDayList(transactions))}")
                setListAndCalculateData(filterRecentCurrentDayList(transactions))
            }
            weekChip.isChecked -> {
                setListAndCalculateData(filterRecentCurrentWeekList(transactions))
            }
            else -> {
                setListAndCalculateData(transactions)
            }
        }
    }

    private fun setListAndCalculateData(transactions: List<Transaction>) = with(binding){
        Log.i("Filter", "transaction:: $transactions")
        val (totalIncome, totalExpense) = transactions.partition { it.transactionType == Income }
        val income = totalIncome.sumOf { it.amount }
        val expense = totalExpense.sumOf { it.amount }
        tvIncome.text = indianRupee(income)
        tvExpense.text = indianRupee(expense)
        tvAccountBalance.text = indianRupee(income - expense)
        mAdapter.submitList(transactions)
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

    private fun filterRecentCurrentDayList(transaction: List<Transaction>): List<Transaction> {
        val transactionList: MutableList<Transaction> = mutableListOf()
        transaction.forEach {
            if (it.date.contentEquals(getCurrentDate())) {
                transactionList.add(it)
            }
        }
        return transactionList
    }

    private fun filterRecentCurrentWeekList(transaction: List<Transaction>): List<Transaction> {
        val transactionList: MutableList<Transaction> = mutableListOf()
        transaction.forEach {
            if (isThisIsInCurrentWeek(it.date)) {
                transactionList.add(it)
            }
        }
        return transactionList
    }

    fun isBetween(input: Date, date1: Date, date2: Date): Boolean? {
        return input > date1 && input < date2
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
                    setRecentTransactionList(filterList(uiState.transaction))
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

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onTransactionClick(transaction: Transaction) {

    }


}