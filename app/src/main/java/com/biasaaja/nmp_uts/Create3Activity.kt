package com.biasaaja.nmp_uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.ActivityCreate3Binding
import org.json.JSONObject

class Create3Activity : AppCompatActivity() {
    private  lateinit var  binding: ActivityCreate3Binding
    private lateinit var shared: SharedPreferences
    private lateinit var title:String
    private lateinit var desc:String
    private lateinit var genre:String
    private lateinit var access:String
    private lateinit var para:String
    private lateinit var imgurl:String

    companion object{
        val KEY_TITLE = "TITLE"
        val KEY_DESC = "DESC"
        val KEY_IMG = "IMG"
        val KEY_GENRE = "GENRE"
        val KEY_ACC = "ACC"
        val KEY_PAR = "PAR"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreate3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        access = intent.getStringExtra(Create3Activity.KEY_ACC).toString()
        desc = intent.getStringExtra(Create3Activity.KEY_DESC).toString()
        genre = intent.getStringExtra(Create3Activity.KEY_GENRE).toString()
        title = intent.getStringExtra(Create3Activity.KEY_TITLE).toString()
        para = intent.getStringExtra(Create3Activity.KEY_PAR).toString()
        imgurl = intent.getStringExtra(Create3Activity.KEY_IMG).toString()

        binding.lblAccess.text = access
        binding.lblDescription.text = desc
        binding.lblGenre.text = genre
        binding.lblTitle.text = title
        binding.lblParagraph.text = para

        binding.btnPrev.setOnClickListener {
            val intent2 = Intent(this, Create2Activity::class.java)
            intent2.putExtra(KEY_TITLE, intent.getStringExtra(Create3Activity.KEY_TITLE))
            intent2.putExtra(KEY_DESC, intent.getStringExtra(Create3Activity.KEY_DESC))
            intent2.putExtra(KEY_IMG, intent.getStringExtra(Create3Activity.KEY_IMG))
            intent2.putExtra(KEY_GENRE, intent.getStringExtra(Create3Activity.KEY_GENRE))
            intent2.putExtra(KEY_ACC, intent.getStringExtra(Create3Activity.KEY_ACC))
            intent2.putExtra(KEY_PAR, intent.getStringExtra(Create3Activity.KEY_PAR))

            startActivity(intent2)
        }

        binding.btnPublish.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage("Are you sure about publishing this story?")
            builder.setNegativeButton("CANCEL", null)
            builder.setPositiveButton("PUBLISH") { dialogInterface, i ->
                createHelper()
            }
            builder.create().show()
        }
    }

    fun createHelper(){
        shared = getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)
        val user_id = shared.getInt("user_id",0)
        val q = Volley.newRequestQueue(this)
        var url = "https://ubaya.me/native/160421119/cerbung_create.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("Volley",it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val message:String = obj.getString("message")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            },
            {
                Toast.makeText(this, it.message.toString() , Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                var isPrivate = 0
                if(access != "Public") {
                    isPrivate = 1
                }
                val params = HashMap<String, String>()
                params["user_id"] = user_id.toString()
                params["title"] = title
                params["desc"] = desc
                params["cover_url"] = imgurl
                params["genre"] = genre
                params["is_private"] = isPrivate.toString()
                params["content"] = para
                return params
            }
        }
        q.add(stringRequest)
    }

}