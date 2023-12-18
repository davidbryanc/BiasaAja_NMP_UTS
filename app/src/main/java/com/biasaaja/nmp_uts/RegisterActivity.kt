package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.ActivityRegisterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if(binding.txtPassword.text.toString().equals(binding.txtPassword2.text.toString())){

                var username = binding.txtUsername.text.toString()
                var password = binding.txtPassword.text.toString()
                var profile_url = binding.txtPicture.text.toString()

                var q = Volley.newRequestQueue(this)
                val url = "http://ubaya.me/native/160421119/user_login.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            val message:String = obj.getString("message")
                            Toast.makeText(applicationContext, message ,Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Toast.makeText(applicationContext, it.message.toString() ,Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["password"] = password
                        params["profile_url"] = profile_url
                        return params
                    }
                }
                q.add(stringRequest)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(it.context, "Password and password confirmation are different", Toast.LENGTH_SHORT).show()
            }
        }
    }
}