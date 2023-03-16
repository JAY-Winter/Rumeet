package com.d204.rumeet.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d204.rumeet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}