package com.dev_yogesh.montra.ui.fragment.HomeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.ui.MainActivity
import com.dev_yogesh.montra.utils.getCurrentMonth
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

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


    }
    private fun initListener()= with(binding){
        ivSpendFrequency.setOnClickListener {
            graphVisibility()
        }
        labelSpendFrequency.setOnClickListener {
            graphVisibility()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = HomeFragment()
    }
}