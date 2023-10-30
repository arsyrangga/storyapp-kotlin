package com.rangga.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rangga.storyapp.R
import com.rangga.storyapp.data.parcel.LoginParcel
import com.rangga.storyapp.databinding.ActivityMainBinding
import com.rangga.storyapp.helper.ViewModelFactoryMain
import com.rangga.storyapp.model.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, ViewModelFactoryMain(applicationContext)).get(
            MainViewModel::class.java
        )
        viewModel.loading.observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.getToken()

        viewModel.token.observe(this, Observer {
            if(it != ""){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity((intent))
            }
        })


        viewModel.showToastMessage.observe(this, Observer { data ->
            data?.let {
                if (!it) {
                    Toast.makeText(
                        this@MainActivity,
                        "Login Gagal Silahkan Cek Kembali Kredensial Anda",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerText.setOnClickListener(this)
        binding.submit.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.registerText -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity((intent))
            }

            R.id.submit -> {

                val email = binding.emailInput.text.toString()
                val password = binding.passwordInput.text.toString()
                if(password.length > 7){
                    val data = LoginParcel(email, password)
                    viewModel.login(data)
                }
            }
        }
    }

    override fun onBackPressed() {
    }

}

