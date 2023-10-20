package com.rangga.storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rangga.storyapp.R
import com.rangga.storyapp.databinding.ActivityAddStoryBinding
import com.rangga.storyapp.databinding.ActivityDetailStoryBinding
import com.rangga.storyapp.helper.ViewModelFactoryMain
import com.rangga.storyapp.model.AddStoryViewModel
import com.rangga.storyapp.model.DetailStoryViewModel

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var viewModel: DetailStoryViewModel

    companion object {
        const val Id = "id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactoryMain(applicationContext)).get(
            DetailStoryViewModel::class.java
        )
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(Id)
        viewModel.getDetail(id.toString())

        viewModel.dataResponse.observe(this, Observer{
            Glide.with(applicationContext)
                .load(it?.story?.photoUrl)
                .into(binding.imageDetail)

            binding.nameDetail.text = it?.story?.name.toString()
            binding.descDetail.text = it?.story?.description.toString()
        })

        supportActionBar?.title = "Detail Story"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}