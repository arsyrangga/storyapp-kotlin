package com.rangga.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangga.storyapp.R
import com.rangga.storyapp.adapter.ListStoryAdapter
import com.rangga.storyapp.databinding.ActivityHomeBinding
import com.rangga.storyapp.helper.SessionManager
import com.rangga.storyapp.helper.ViewModelFactoryHome
import com.rangga.storyapp.model.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactoryHome(this)
    }
    private lateinit var rvStory: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvStory = binding.rvStory


        getData()


        binding.btnPlus.setOnClickListener(this)
        supportActionBar?.title = "The Stories"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    fun logout() {
        val scope = CoroutineScope(Dispatchers.Default)
        val sessionManager = SessionManager(applicationContext)
        scope.launch {
            sessionManager.clearAuthToken()
        }
    }

    private fun getData() {
        rvStory.layoutManager = GridLayoutManager(this, 1)
        val adapter = ListStoryAdapter()
        rvStory.adapter = adapter
        viewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.menu_item -> {
                logout()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
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