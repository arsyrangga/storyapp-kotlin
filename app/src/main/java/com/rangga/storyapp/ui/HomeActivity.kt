package com.rangga.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangga.storyapp.R
import com.rangga.storyapp.adapter.ListStoryAdapter
import com.rangga.storyapp.data.response.ListStoryDataResponse
import com.rangga.storyapp.data.retrofit.AuthInterceptor
import com.rangga.storyapp.databinding.ActivityHomeBinding
import com.rangga.storyapp.databinding.ActivityMainBinding
import com.rangga.storyapp.helper.TokenDatastore
import com.rangga.storyapp.helper.ViewModelFactoryMain
import com.rangga.storyapp.helper.dataStore
import com.rangga.storyapp.model.HomeViewModel

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var rvStory: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvStory = binding.rvStory
        viewModel = ViewModelProvider(this, ViewModelFactoryMain(applicationContext)).get(
            HomeViewModel::class.java
        )

        viewModel.getData()
        viewModel.list.observe(this){
                showRecyclerList(it)
        }
        binding.btnPlus.setOnClickListener(this)
        supportActionBar?.title = "The Stories"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.removeToken()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
    }

    private fun showRecyclerList(list: List<ListStoryDataResponse>) {
        rvStory.layoutManager = GridLayoutManager(this, 1)
        val listAccountAdapter = ListStoryAdapter(list)
        rvStory.adapter = listAccountAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_plus -> {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }


}