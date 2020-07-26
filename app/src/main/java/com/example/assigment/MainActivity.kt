package com.example.assigment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assigment.UI.Login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.main, LoginFragment.newInstance()).commit()
    }
}