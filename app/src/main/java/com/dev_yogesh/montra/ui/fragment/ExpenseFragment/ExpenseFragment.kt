package com.dev_yogesh.montra.ui.fragment.ExpenseFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentBudgetBinding
import com.dev_yogesh.montra.databinding.FragmentExpenseBinding
import com.dev_yogesh.montra.ui.fragment.BudgetFragment.BudgetFragment
import com.dev_yogesh.montra.utils.Dialogs.selectTransactionTypeDialog
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import com.dev_yogesh.montra.utils.comon.DialogTransactionTypeCallback

class ExpenseFragment : Fragment() {
    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!
    private var previousSelectedType =7

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
    }

    private fun setData()= with(binding){

    }
    private fun initListener()= with(binding){

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        etExpenseAmount.requestFocus()
        tvExpenseCategory.setOnClickListener {
            selectTransactionTypeDialog(requireContext(),previousSelectedType,object : DialogTransactionTypeCallback {

                override fun selectedType(type: String, typeInt: Int, drawable: Drawable) {
                    tvExpenseCategory.text =type
                    previousSelectedType = typeInt
                 }
            })
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = ExpenseFragment()
    }
}
