package com.biasaaja.nmp_uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user:User
    private lateinit var sharedPreferences:SharedPreferences
    private var isDarkTheme:Boolean = false
    companion object{
        val KEY_USERNAME = "USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkDark()

        binding.btnLogin.setOnClickListener {
//            var login = false
            var username = binding.txtUsername.text.toString()
            var password = binding.txtPassword.text.toString()

            var q = Volley.newRequestQueue(this)
            val url = "https://ubaya.me/native/160421119/user_login.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("Volley",it)
                    val obj = JSONObject(it)
                    val message:String = obj.getString("message")
                    if(obj.getString("result") == "OK") {
                        user = User(obj.getJSONObject("data").getInt("id")
                            ,obj.getJSONObject("data").getString("username")
                            ,obj.getJSONObject("data").getString("password")
                            ,obj.getJSONObject("data").getString("profile_url")
                            ,obj.getJSONObject("data").getString("date_joined"))
                        Toast.makeText(applicationContext, message ,Toast.LENGTH_SHORT).show()

                        sharedPreferences = getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putInt("user_id", user.id)
                        editor.putString("username", user.username)
                        editor.putString("profile_url", user.profile_url)
                        editor.putString("date_joined",user.date_joined)

                        editor.apply()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(KEY_USERNAME, user.username)
                        startActivity(intent)
                        this.finish()

                    }else{
                        Toast.makeText(applicationContext, message.toString() ,Toast.LENGTH_SHORT).show()
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
                    return params
                }
            }
            q.add(stringRequest)

        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun checkDark(){
        sharedPreferences = getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)
        isDarkTheme = sharedPreferences.getBoolean("dark",false)
        if(isDarkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}