package com.dev_yogesh.montra.ui.fragment.HomeFragment.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.dev_yogesh.montra.databinding.RvItemRecentTransactionBinding
import com.dev_yogesh.montra.model.Transaction
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel.Companion.EXPENSE
import com.dev_yogesh.montra.utils.comon.ImageUtils.getDrawableFromItem
import indianRupee

class RecentTransactionAdapter (private val listener: ItemListener) :
    ListAdapter<Transaction, RecentTransactionAdapter.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemRecentTransactionBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: Transaction = getItem(position)
        currentItem.let {
            holder.bind(it,listener)
        }
    }


    class ViewHolder(private val binding: RvItemRecentTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction,listener: ItemListener) {

            binding.apply {

                binding.root.setOnClickListener {
                    listener.onTransactionClick(transaction)
                }
                Log.i("Filter", "transaction:: ${transaction}")
                tvSpendItemName.text= transaction.tag
                tvSpendItemAmount.text= indianRupee(transaction.amount)
                tvSpendItemNote.text= transaction.note
                if(transaction.transactionType.contentEquals(EXPENSE)){
                    tvSpendItemAmount.setTextColor(Color.RED)
                }else{
                    tvSpendItemAmount.setTextColor(Color.GREEN)
                }
                ivItemImage.setImageDrawable(getDrawableFromItem(transaction.tag,binding.root.context))
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface ItemListener {
        fun onTransactionClick(transaction: Transaction)
    }


}