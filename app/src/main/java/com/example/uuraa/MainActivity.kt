package com.example.uuraa

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uuraa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        TOKEN = pref.getString("TOKEN","token")!!
        binding.button.setOnClickListener {
            POST.getToken { token ->
                val editor = pref.edit()
                editor.putString("TOKEN",token)
                editor.apply()
                TOKEN = token
                Toast.makeText(applicationContext, TOKEN, Toast.LENGTH_SHORT).show()
            }
        }
        binding.button1.setOnClickListener {
            POST.getUser(this)
        }

    }
}