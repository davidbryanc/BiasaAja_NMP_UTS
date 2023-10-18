package com.biasaaja.nmp_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.biasaaja.nmp_uts.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lm: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = CerbungAdapter()
    }
}