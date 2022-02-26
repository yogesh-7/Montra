package com.dev_yogesh.montra.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.DialogSelectAMonthBinding
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import java.util.*

object Dialogs {


    fun selectMonthDialog(context: Context,previousSelectedMonth:Int,callback: DialogMonthCallback) {
        val dialog = Dialog(
            context, R.style.FullWidthDialog3
        )
        //val calendar: Calendar = Calendar.getInstance()
        //calendar.get(Calendar.MONTH)
        dialog.window?.setGravity(Gravity.CENTER_VERTICAL)
        val layoutInflater = LayoutInflater.from(context)
        val binding: DialogSelectAMonthBinding = DialogSelectAMonthBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.apply {
            when (previousSelectedMonth) {
                0 -> januaryChip.isChecked = true
                1 -> februaryChip.isChecked = true
                2 -> marchChip.isChecked = true
                3 -> aprilChip.isChecked = true
                4 -> mayChip.isChecked = true
                5 -> juneChip.isChecked = true
                6 -> julyChip.isChecked = true
                7 -> augustChip.isChecked = true
                8 -> septemberChip.isChecked = true
                9 -> octoberChip.isChecked = true
                10 -> novemberChip.isChecked = true
                11 -> decemberChip.isChecked = true

            }

            januaryChip.setOnClickListener {
                callback.selectedMonth(januaryChip.text.toString(),0)
                dialog.dismiss()
            }
            februaryChip.setOnClickListener {
                callback.selectedMonth(februaryChip.text.toString(),1)
                dialog.dismiss()
            }
            marchChip.setOnClickListener {
                callback.selectedMonth(marchChip.text.toString(),2)
                dialog.dismiss()
            }
            aprilChip.setOnClickListener {
                callback.selectedMonth(aprilChip.text.toString(),3)
                dialog.dismiss()
            }
            mayChip.setOnClickListener {
                callback.selectedMonth(mayChip.text.toString(),4)
                dialog.dismiss()
            }
            juneChip.setOnClickListener {
                callback.selectedMonth(juneChip.text.toString(),5)
                dialog.dismiss()
            }
            julyChip.setOnClickListener {
                callback.selectedMonth(julyChip.text.toString(),6)
                dialog.dismiss()
            }
            augustChip.setOnClickListener {
                callback.selectedMonth(augustChip.text.toString(),7)
                dialog.dismiss()
            }
            septemberChip.setOnClickListener {
                callback.selectedMonth(septemberChip.text.toString(),8)
                dialog.dismiss()
            }
            octoberChip.setOnClickListener {
                callback.selectedMonth(octoberChip.text.toString(),9)
                dialog.dismiss()
            }
            novemberChip.setOnClickListener {
                callback.selectedMonth(novemberChip.text.toString(),10)
                dialog.dismiss()
            }
            decemberChip.setOnClickListener {
                callback.selectedMonth(decemberChip.text.toString(),11)
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}