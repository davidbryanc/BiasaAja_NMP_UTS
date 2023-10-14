package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if(binding.txtPassword.text.toString().equals(binding.txtPassword2.text.toString())){
                Toast.makeText(it.context, "Sign up successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(it.context, "Password and password confirmation are different", Toast.LENGTH_SHORT).show()
            }
        }
    }
}