package com.dev_yogesh.montra.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.DatePicker
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.DialogSelectAMonthBinding
import com.dev_yogesh.montra.databinding.DialogSelectTransactionTypeBinding
import com.dev_yogesh.montra.utils.comon.DialogMonthCallback
import com.dev_yogesh.montra.utils.comon.DialogTransactionTypeCallback
import java.text.SimpleDateFormat
import java.util.*

object Dialogs {


    fun selectMonthDialog(
        context: Context,
        previousSelectedMonth: Int,
        callback: DialogMonthCallback
    ) {
        val dialog = Dialog(
            context, R.style.FullWidthDialog3
        )
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
                12 -> allChip.isChecked = true
            }
            januaryChip.setOnClickListener {
                callback.selectedMonth(januaryChip.text.toString(), 0)
                dialog.dismiss()
            }
            februaryChip.setOnClickListener {
                callback.selectedMonth(februaryChip.text.toString(), 1)
                dialog.dismiss()
            }
            marchChip.setOnClickListener {
                callback.selectedMonth(marchChip.text.toString(), 2)
                dialog.dismiss()
            }
            aprilChip.setOnClickListener {
                callback.selectedMonth(aprilChip.text.toString(), 3)
                dialog.dismiss()
            }
            mayChip.setOnClickListener {
                callback.selectedMonth(mayChip.text.toString(), 4)
                dialog.dismiss()
            }
            juneChip.setOnClickListener {
                callback.selectedMonth(juneChip.text.toString(), 5)
                dialog.dismiss()
            }
            julyChip.setOnClickListener {
                callback.selectedMonth(julyChip.text.toString(), 6)
                dialog.dismiss()
            }
            augustChip.setOnClickListener {
                callback.selectedMonth(augustChip.text.toString(), 7)
                dialog.dismiss()
            }
            septemberChip.setOnClickListener {
                callback.selectedMonth(septemberChip.text.toString(), 8)
                dialog.dismiss()
            }
            octoberChip.setOnClickListener {
                callback.selectedMonth(octoberChip.text.toString(), 9)
                dialog.dismiss()
            }
            novemberChip.setOnClickListener {
                callback.selectedMonth(novemberChip.text.toString(), 10)
                dialog.dismiss()
            }
            decemberChip.setOnClickListener {
                callback.selectedMonth(decemberChip.text.toString(), 11)
                dialog.dismiss()
            }
            allChip.setOnClickListener {
                callback.selectedMonth(allChip.text.toString(), 12)
                dialog.dismiss()
            }

        }
        dialog.show()
    }


    fun selectTransactionTypeDialog(
        context: Context,
        previousSelectedType: Int,
        callback: DialogTransactionTypeCallback
    ) {
        val dialog = Dialog(
            context, R.style.FullWidthDialog3
        )
        dialog.window?.setGravity(Gravity.CENTER_VERTICAL)
        val layoutInflater = LayoutInflater.from(context)
        val binding: DialogSelectTransactionTypeBinding = DialogSelectTransactionTypeBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            when (previousSelectedType) {
                0 -> housingChip.isChecked = true
                1 -> transportationChip.isChecked = true
                2 -> foodChip.isChecked = true
                3 -> entertainmentChip.isChecked = true
                4 -> healthcareChip.isChecked = true
                5 -> savingAmpDebtsinsuranceChip.isChecked = true
                6 -> personalSpendingChip.isChecked = true
                7 -> miscellaneousChip.isChecked = true
                8 -> utilitiesChip.isChecked = true
                9 -> insuranceChip.isChecked = true
            }
            housingChip.setOnClickListener {
                callback.selectedType(housingChip.text.toString(), 0,housingChip.chipDrawable)
                dialog.dismiss()
            }
            transportationChip.setOnClickListener {
                callback.selectedType(transportationChip.text.toString(), 1,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            foodChip.setOnClickListener {
                callback.selectedType(foodChip.text.toString(), 2,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            entertainmentChip.setOnClickListener {
                callback.selectedType(entertainmentChip.text.toString(), 3,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            healthcareChip.setOnClickListener {
                callback.selectedType(healthcareChip.text.toString(), 4,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            savingAmpDebtsinsuranceChip.setOnClickListener {
                callback.selectedType(savingAmpDebtsinsuranceChip.text.toString(), 5,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            personalSpendingChip.setOnClickListener {
                callback.selectedType(personalSpendingChip.text.toString(), 6,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            miscellaneousChip.setOnClickListener {
                callback.selectedType(miscellaneousChip.text.toString(), 7,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            utilitiesChip.setOnClickListener {
                callback.selectedType(utilitiesChip.text.toString(), 8,transportationChip.chipDrawable)
                dialog.dismiss()
            }
            insuranceChip.setOnClickListener {
                callback.selectedType(insuranceChip.text.toString(), 9,transportationChip.chipDrawable)
                dialog.dismiss()
            }
        }
        dialog.show()
    }



}