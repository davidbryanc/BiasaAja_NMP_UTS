package com.biasaaja.nmp_uts

import android.content.Context
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
//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        val sharedPreferences = getSharedPreferences("userPref", Context.MODE_PRIVATE)
//
////        val userId = sharedPreferences.getInt("user_id", 0)
//        val username = sharedPreferences.getString("username", "")
////        val profile_url = sharedPreferences.getString("profile_url", "")
//
//
//        val lm: LinearLayoutManager = LinearLayoutManager(this)
//        binding.recyclerView.layoutManager = lm
//        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView.adapter = CerbungAdapter(cerbungs)
//
//        binding.btnCreate.setOnClickListener(){
//            val intent = Intent(this, Create1Activity::class.java)
//            intent.putExtra(KEY_USERNAME, username)
//            startActivity(intent)
//        }
    }
}