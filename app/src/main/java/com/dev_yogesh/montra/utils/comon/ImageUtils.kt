package com.dev_yogesh.montra.utils.comon

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import com.dev_yogesh.montra.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


object ImageUtils {

    private const val IMAGE_DIRECTORY = "Montra"

    fun getBitmapFromUri(context: Context, imageUri: Uri): Bitmap {
        val stream = context.contentResolver?.openInputStream(imageUri)
        return BitmapFactory.decodeStream(stream)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawableFromItem(type: String, context: Context): Drawable {
        val drawable = if (type.contentEquals("Housing")) {
            context.resources.getDrawable(R.drawable.housing, null)
        } else if (type.contentEquals("Food")) {
            context.resources.getDrawable(R.drawable.food, null)
        } else if (type.contentEquals("Transportation")) {
            context.resources.getDrawable(R.drawable.transportation, null)
        } else if (type.contentEquals("Utilities")) {
            context.resources.getDrawable(R.drawable.utilities, null)
        } else if (type.contentEquals("Insurance")) {
            context.resources.getDrawable(R.drawable.insurance, null)
        } else if (type.contentEquals("Entertainment")) {
            context.resources.getDrawable(R.drawable.entertainment, null)
        } else if (type.contentEquals("Saving & Debt Insurance")) {
            context.resources.getDrawable(R.drawable.saving_and_debt, null)
        } else if (type.contentEquals("Personal Spending")) {
            context.resources.getDrawable(R.drawable.personal_spending, null)
        } else if (type.contentEquals("Miscellaneous")) {
            context.resources.getDrawable(R.drawable.miscellaneous, null)
        } else if (type.contentEquals("Healthcare")) {
            context.resources.getDrawable(R.drawable.healthcare, null)
        }else{
            context.resources.getDrawable(R.drawable.ic_shopping_bag, null)
        }

        ///return (drawable as BitmapDrawable).bitmap
        return drawable
    }



    fun getFileFromUri(context: Context, imageUri: Uri): File {
        val streamInput = context.contentResolver?.openInputStream(imageUri)
        val bitmap= BitmapFactory.decodeStream(streamInput)

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.png")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
        //Timber.d("ImagePath:::: ${file.absolutePath}")

        return file
    }

    fun saveImageToInternalStorage(context: Context,bitmap: Bitmap): File {

        // Get the context wrapper instance
        val wrapper = ContextWrapper(context)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.png")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
       // Timber.d("ImagePath:::: ${file.absolutePath}")

        return file
    }
}