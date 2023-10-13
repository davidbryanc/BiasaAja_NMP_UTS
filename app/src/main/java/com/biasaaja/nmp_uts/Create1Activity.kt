package com.biasaaja.nmp_uts

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityCreate1Binding

class Create1Activity : AppCompatActivity() {
    private  lateinit var  binding: ActivityCreate1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreate1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.genre)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinGenre.adapter = adapter


    }
}