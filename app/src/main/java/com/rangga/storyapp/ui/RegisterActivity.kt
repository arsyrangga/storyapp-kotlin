package com.rangga.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rangga.storyapp.R
import com.rangga.storyapp.databinding.ActivityMainBinding
import com.rangga.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginText -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity((intent))
            }
        }
    }
}