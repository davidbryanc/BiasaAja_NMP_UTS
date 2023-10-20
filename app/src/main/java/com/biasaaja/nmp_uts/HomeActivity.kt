package com.biasaaja.nmp_uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.biasaaja.nmp_uts.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    companion object{
        val KEY_USERNAME = "USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lm: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = CerbungAdapter()

        binding.btnCreate.setOnClickListener(){
            val intent = Intent(this, Create1Activity::class.java)
            intent.putExtra(KEY_USERNAME, LoginActivity.KEY_USERNAME)
            startActivity(intent)
        }
    }
}