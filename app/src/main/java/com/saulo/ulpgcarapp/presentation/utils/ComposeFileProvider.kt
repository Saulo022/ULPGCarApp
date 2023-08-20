package com.saulo.ulpgcarapp.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.saulo.ulpgcarapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class ComposeFileProvider : FileProvider(R.xml.file_paths) {

    companion object {
        fun getImageUri(context: Context): Uri {

            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context, authority, file
            )
        }

        fun getPathFromBitmap(context: Context, bitmap: Bitmap): String {
            val wrapper = ContextWrapper(context)
            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file,"${UUID.randomUUID()}.jpg")
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
            return file.path
        }

    }
}