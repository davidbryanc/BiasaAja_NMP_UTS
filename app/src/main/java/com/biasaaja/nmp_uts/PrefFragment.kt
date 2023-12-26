package com.biasaaja.nmp_uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.FragmentPrefBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class PrefFragment : Fragment() {
    private lateinit var binding:FragmentPrefBinding
    private lateinit var shared:SharedPreferences
//    var username = shared.getString(LoginActivity.TOWER.toString(),"").toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = requireActivity().getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrefBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtUsernameProf.text = shared.getString("username","").toString()
        var url = shared.getString("profile_url","https://i.stack.imgur.com/l60Hf.png").toString()
//        val builder = Picasso.Builder(requireActivity())
//        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Log.d("URLNYa", url)
        Picasso.get().load(url).into(binding.imgPP)
        binding.switchDark.isChecked = shared.getBoolean("dark",false) == true
        binding.switchNotif.isChecked = shared.getBoolean("notif",false) == true

        binding.switchDark.setOnClickListener{
            val editor = shared.edit()
            editor.putBoolean("dark",binding.switchDark.isChecked)
            editor.apply()
        }

        binding.switchNotif.setOnClickListener{
            val editor = shared.edit()
            editor.putBoolean("notif",binding.switchNotif.isChecked)
            editor.apply()
        }

        binding.btnChange.setOnClickListener {
            if(binding.editTextNew.text == binding.editTextRetype.text){
                var username = shared.getString("username","")
                var old = binding.editTextOld.text.toString()
                var new = binding.editTextNew.text.toString()

                var q = Volley.newRequestQueue(this.context)
                val url = "https://ubaya.me/native/160421119/user_changepass.php"

                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("Volley",it)
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            val message:String = obj.getString("message")
                            Toast.makeText(this.context, message , Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Toast.makeText(this.context, it.message.toString() , Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = username.toString()
                        params["old"] = old
                        params["new"] = new
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }

        binding.btnSignOut.setOnClickListener {
            val editor = shared.edit()
            editor.putInt("user_id", 0)
            editor.putString("username", "")
            editor.putString("profile_url", "")

            editor.apply()
            activity?.let {
                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }


        }
    }
}