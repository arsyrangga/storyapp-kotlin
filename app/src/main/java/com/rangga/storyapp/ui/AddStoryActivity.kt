package com.rangga.storyapp.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loopj.android.http.BuildConfig
import com.rangga.storyapp.R
import com.rangga.storyapp.databinding.ActivityAddStoryBinding
import com.rangga.storyapp.databinding.ActivityHomeBinding
import com.rangga.storyapp.databinding.ActivityMainBinding
import com.rangga.storyapp.helper.TokenDatastore
import com.rangga.storyapp.helper.Utils.Companion.uriToFile
import com.rangga.storyapp.helper.ViewModelFactoryMain
import com.rangga.storyapp.helper.dataStore
import com.rangga.storyapp.model.AddStoryViewModel
import com.rangga.storyapp.model.HomeViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddStoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var viewModel: AddStoryViewModel
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryMain(applicationContext)).get(
            AddStoryViewModel::class.java
        )
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGallery.setOnClickListener(this)
        binding.btnCamera.setOnClickListener(this)
        binding.btnUpload.setOnClickListener(this)

        viewModel.isSuccess.observe(this, Observer {
            binding.progressBar.visibility = View.INVISIBLE
            if (it){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity((intent))
            }
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            } else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        supportActionBar?.title = "Add Story"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            Glide.with(applicationContext)
                .load(uri)
                .into(binding.imageView)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

    fun getImageUri(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri ?: getImageUriForPreQ(context)
    }

    private fun getImageUriForPreQ(context: Context): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            Glide.with(applicationContext)
                .load(currentImageUri)
                .into(binding.imageView)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_gallery -> {
                startGallery()
            }
            R.id.btn_camera ->{
                startCamera()
            }
            R.id.btnUpload ->{
                currentImageUri?.let {
                    viewModel.uploadStories(it, binding.descInput.text.toString())

                }

            }


        }
    }
}