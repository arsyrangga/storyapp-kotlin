package com.rangga.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rangga.storyapp.R
import com.rangga.storyapp.data.parcel.RegistParcel
import com.rangga.storyapp.databinding.ActivityMainBinding
import com.rangga.storyapp.databinding.ActivityRegisterBinding
import com.rangga.storyapp.helper.ViewModelFactory
import com.rangga.storyapp.model.RegisterViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this@RegisterActivity)
        binding.loginText.setOnClickListener(this)

        viewModel.loading.observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.showToastMessage.observe(this, Observer { data ->
            data?.let {
                if (it) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Akun Berhasil Dibuat",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity((intent))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Akun gagal dibuat silahkan ganti email yang lain",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewModel.showToastMessage.value = null
            }
        })


    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginText -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity((intent))
            }

            R.id.submit -> {
                val name = binding.nameInput.text.toString()
                val email = binding.emailInput.text.toString()
                val password = binding.passwordInput.text.toString()
                if(password.length > 7){
                    val data = RegistParcel(name, email, password)
                    viewModel.Register(data)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RegisterViewModel::class.java)
    }
}