package com.rangga.storyapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.Instant

class Utils {




    companion object {
        @SuppressLint("NewApi")
        fun createCustomTempFile(context: Context): File {
            val filesDir = context.externalCacheDir
            return File.createTempFile(Instant.now().toString(), ".jpg", filesDir)
        }
        fun uriToFile(imageUri: Uri, context: Context): File {
            val myFile = createCustomTempFile(context)
            val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
            val outputStream = FileOutputStream(myFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
            outputStream.close()
            inputStream.close()
            return myFile
        }
    }
}