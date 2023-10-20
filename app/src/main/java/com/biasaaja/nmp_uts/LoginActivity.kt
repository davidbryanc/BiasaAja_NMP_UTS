package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    companion object{
        val KEY_USERNAME = "USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var login = false
            var username = ""
            for (user in Global.users)
            {
                if(binding.txtUsername.text.toString().equals(user.username)
                    && binding.txtPassword.text.toString().equals(user.password)) {
                    login = true
                    username = user.username
                }
            }
            if(!login) Toast.makeText(it.context, "Wrong username or password", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(it.context, "Sign in successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(KEY_USERNAME, username)
                startActivity(intent)
                this.finish()
            }
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}