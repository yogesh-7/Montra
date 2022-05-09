package com.dev_yogesh.montra.ui.fragment.ExpenseFragment

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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.dev_yogesh.montra.R
import com.dev_yogesh.montra.databinding.DialogBottomSheetAddFileBinding
import com.dev_yogesh.montra.databinding.FragmentExpenseBinding
import com.dev_yogesh.montra.model.Transaction
import com.dev_yogesh.montra.ui.comon.BaseFragment
import com.dev_yogesh.montra.ui.viewModel.TransactionViewModel
import com.dev_yogesh.montra.utils.Dialogs.selectTransactionTypeDialog
import com.dev_yogesh.montra.utils.comon.DialogTransactionTypeCallback
import com.dev_yogesh.montra.utils.comon.ImageUtils.getBitmapFromUri
import com.dev_yogesh.montra.utils.comon.ImageUtils.saveImageToInternalStorage
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
class ExpenseFragment : BaseFragment<FragmentExpenseBinding, TransactionViewModel>() {


    override val viewModel: TransactionViewModel by activityViewModels()
    private var previousSelectedType = 7

    lateinit var file: File
    var filePath = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        initListener()
    }

    private fun setData() = with(binding) {

    }

    private fun initListener() = with(binding) {
        etExpenseAmount.requestFocus()

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tvExpenseCategory.setOnClickListener {
            openCategoryDialog()
        }
        rlExpenseAddAttachment.setOnClickListener {
            openFileBottomSheet()
        }
        ivEditExpenseAttachment.setOnClickListener {
            openFileBottomSheet()
        }

        tvExpenseDate.setOnClickListener {
            showDateDialog()
        }
        btnContinue.setOnClickListener {
           if (validateTransaction(getTransactionContent())) {
                viewModel.insertTransaction(getTransactionContent()).run {
                    binding.root.snack(string = R.string.success_expense_saved)
                    findNavController().popBackStack()
                }
            }

            /*viewModel.insertTransaction(getTransactionContent()).run {
                binding.root.snack(string = R.string.success_expense_saved)
                findNavController().popBackStack()
            }*/
        }
    }


    private fun validateTransaction(transaction:Transaction):Boolean{

        Log.i("Filter", "isBlanks ${transaction.amount}")
        Log.i("Filter", "isBlanks ${(transaction.amount>0.0)}")
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
        val title = it.tvExpenseCategory.text.toString()
        val amount = parseDouble(it.etExpenseAmount.text.toString())
        val transactionType = "Expense"
        val tag = it.tvExpenseCategory.text.toString()

        val receipt = filePath
        val date = it.tvExpenseDate.text.toString()
        val note = it.tvExpenseDescription.text.toString()

        return Transaction(title, amount, transactionType, tag, receipt, date, note)
    }

    private fun showDateDialog() {
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.datepicker,
            { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)

                binding.tvExpenseDate.text = sdf.format(myCalendar.time)
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        //  datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun openCategoryDialog() {
        selectTransactionTypeDialog(
            requireContext(),
            previousSelectedType,
            object : DialogTransactionTypeCallback {
                override fun selectedType(type: String, typeInt: Int, drawable: Drawable) {
                    binding.tvExpenseCategory.text = type
                    previousSelectedType = typeInt
                }
            })
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
                    file = saveImageToInternalStorage(requireContext(), thumbnail)
                    //uploadFile()

                    showFileInUi(thumbnail)
                    filePath = file.absolutePath
                    //viewModel.requestUploadPhoto(file)
                }
            } else if (requestCode == GALLERY) {
                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data
                    val thumbnail: Bitmap =
                        getBitmapFromUri(requireContext(), selectedPhotoUri!!)
                    file = saveImageToInternalStorage(requireContext(), thumbnail)
                    showFileInUi(thumbnail)
                    filePath = file.absolutePath
                    //uploadFile()
                    // viewModel.requestUploadPhoto(file)
                }
            }
        }
    }

    private fun showFileInUi(thumbnail: Bitmap) {
        binding.apply {
            tvExpenseAddAttachment.isVisible = false
            ivExpenseAttachment.isVisible = true
            ivEditExpenseAttachment.isVisible = true
            ivExpenseAttachment.load(thumbnail) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }


    companion object {
        val TAG = this::class.toString()
        fun getInstance() = ExpenseFragment()
        private const val CAMERA = 1
        private const val GALLERY = 2
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExpenseBinding.inflate(inflater, container, false)
}
