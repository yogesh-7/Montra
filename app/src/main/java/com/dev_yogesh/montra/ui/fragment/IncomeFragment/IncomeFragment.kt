package com.dev_yogesh.montra.ui.fragment.IncomeFragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.DialogBottomSheetAddFileBinding
import com.dev_yogesh.montra.databinding.FragmentHomeBinding
import com.dev_yogesh.montra.databinding.FragmentIncomeBinding
import com.dev_yogesh.montra.model.Transaction
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.Dialogs
import com.dev_yogesh.montra.utils.comon.DialogTransactionTypeCallback
import com.dev_yogesh.montra.utils.comon.ImageUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import parseDouble
import snack
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding, TransactionViewModel>() {

    private var previousSelectedType = 7

    lateinit var file: File
     var filePath =""

    override val viewModel: TransactionViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentIncomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
    }

    private fun setData() = with(binding) {

    }

    private fun initListener() = with(binding) {
        etIncomeAmount.requestFocus()

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tvIncomeCategory.setOnClickListener {
            openCategoryDialog()
        }
        rlIncomeAddAttachment.setOnClickListener {
            openFileBottomSheet()
        }
        ivEditIncomeAttachment.setOnClickListener {
            openFileBottomSheet()
        }

        tvIncomeDate.setOnClickListener {
            showDateDialog()
        }


        btnContinue.setOnClickListener {
            if(validateTransaction(getTransactionContent())){
                viewModel.insertTransaction(getTransactionContent()).run {
                    binding.root.snack(string = R.string.success_expense_saved)
                    findNavController().popBackStack()
                }
            }

        }


    }

   private fun validateTransaction(transaction:Transaction):Boolean{


       if(transaction.amount.toString().contentEquals("NaN")){
           toast("Please enter the amount")
           return false
       }
       if(transaction.amount<1){
           toast("Please enter a valid amount")
           return false
       }
       if(transaction.date.isBlank()){
           toast("Please select a date")
           return false
       }
        return true



    }

    private fun getTransactionContent(): Transaction = binding.let {
        val title = it.tvIncomeCategory.text.toString()
        val amount = parseDouble(it.etIncomeAmount.text.toString())
        val transactionType = "Income".toString()
        val tag = it.tvIncomeCategory.text.toString()

        val receipt = filePath
        val date = it.tvIncomeDate.text.toString()
        val note = it.tvIncomeDescription.text.toString()

        return Transaction(title, amount, transactionType, tag,receipt, date, note)
    }

    private fun openFileBottomSheet() {
        val bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.CustomerBottomSheetDialog)
        val bindingBottomSheet =
            DialogBottomSheetAddFileBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.apply {
            setContentView(bindingBottomSheet.root)
            setCanceledOnTouchOutside(true)
            behavior.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
            }
        }

        bindingBottomSheet.apply {

            cvCamera.setOnClickListener {
                Dexter.withContext(requireContext())
                    .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            report?.let {
                                // Here after all the permission are granted launch the CAMERA to capture an image.
                                if (report.areAllPermissionsGranted()) {
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    startActivityForResult(intent, CAMERA)
                                }
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()//onPermissionRationaleShouldBeShown//CAMERA
                        }
                    }).onSameThread()
                    .check()
                bottomSheetDialog.dismiss()
            }

            cvGallery.setOnClickListener {

                Dexter.withContext(requireContext())
                    .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    .withListener(object : PermissionListener {

                        override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                            // Here after all the permission are granted launch the gallery to select and image.
                            val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )

                            startActivityForResult(galleryIntent, GALLERY)
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            /* Toaster.show(
                                 requireContext(),
                                 getString(R.string.you_denied_permission)
                             )*/
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()//onPermissionRationaleShouldBeShown//GALLERY
                        }
                    }).onSameThread()
                    .check()
                bottomSheetDialog.dismiss()
            }

        }

        bottomSheetDialog.show()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.permissions_required_for_this_feature_msg))
            .setPositiveButton(
                getString(R.string.go_to_settings_caps)
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data?.extras?.let {
                    val thumbnail: Bitmap =
                        data.extras!!.get("data") as Bitmap // Bitmap from camera
                    file = ImageUtils.saveImageToInternalStorage(requireContext(), thumbnail)
                    //uploadFile()

                    showFileInUi(thumbnail)
                    filePath= file.absolutePath
                    //viewModel.requestUploadPhoto(file)
                }
            } else if (requestCode == GALLERY) {
                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data
                    val thumbnail: Bitmap =
                        ImageUtils.getBitmapFromUri(requireContext(), selectedPhotoUri!!)
                    file = ImageUtils.saveImageToInternalStorage(requireContext(), thumbnail)
                    showFileInUi(thumbnail)
                    filePath= file.absolutePath
                    //uploadFile()
                    // viewModel.requestUploadPhoto(file)
                }
            }
        }
    }

    private fun showFileInUi(thumbnail: Bitmap) {
        binding.apply {
            tvIncomeAddAttachment.isVisible = false
            ivIncomeAttachment.isVisible = true
            ivEditIncomeAttachment.isVisible = true
            ivIncomeAttachment.load(thumbnail) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }


    private fun openCategoryDialog() {
        Dialogs.selectTransactionTypeDialog(
            requireContext(),
            previousSelectedType,
            object : DialogTransactionTypeCallback {
                override fun selectedType(type: String, typeInt: Int, drawable: Drawable) {
                    binding.tvIncomeCategory.text = type
                    previousSelectedType = typeInt
                }
            })
    }

    private fun showDateDialog() {
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.datepicker,
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)

                binding.tvIncomeDate.text = sdf.format(myCalendar.time)
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        //  datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }




    companion object {
        val TAG = this::class.toString()
        fun getInstance() = IncomeFragment()
        private const val CAMERA = 1
        private const val GALLERY = 2
    }
}