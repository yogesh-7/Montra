package com.dev_yogesh.montra.ui.fragment.TransactionFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.databinding.FragmentTransactionBinding
import com.dev_yogesh.montra.ui.fragment.HomeFragment.HomeFragment
import com.dev_yogesh.montra.utils.getCurrentMonth


class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
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

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = TransactionFragment()
    }
}