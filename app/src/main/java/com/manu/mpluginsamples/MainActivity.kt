package com.manu.mpluginsamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnClass.setOnClickListener(this)
        btnRes.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnClass -> PluginClassActivity.startPluginClassActivity(this)
            R.id.btnRes -> PluginResActivity.startPluginResActivity(this)
        }
    }
}