package com.et.base.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.et.base.toast.R
import com.et.base.toast.ToastCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ToastCompat.makeText(this, "TEST TOAST", Toast.LENGTH_SHORT).show()
    }
}