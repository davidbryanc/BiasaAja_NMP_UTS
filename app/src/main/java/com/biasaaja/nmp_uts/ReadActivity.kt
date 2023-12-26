package com.biasaaja.nmp_uts

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.ActivityHomeBinding
import com.biasaaja.nmp_uts.databinding.ActivityReadBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadBinding
    private lateinit var cerbung: Cerbung
    var paragraphs: ArrayList<Paragraph> = arrayListOf()
    private lateinit var shared: SharedPreferences

    companion object {
        val ID_CERBUNG = "ID_CERBUNG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shared = getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)

        val cerbungId = intent.getIntExtra(ID_CERBUNG, 0)
        val userId = shared.getInt("user_id",0)
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421119/cerbung_details.php"

        var stringRequest = object : StringRequest(
            Method.POST, url, Response.Listener {
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val cerbungObj = obj.getJSONObject("cerbung")
                    var isPrivate = false
                    if (cerbungObj.getInt("is_private") == 1) {
                        isPrivate = true
                    }
                    var isLiked = false
                    if (cerbungObj.getInt("isLiked") == 1) {
                        isLiked = true
                    }
                    cerbung = Cerbung(
                        cerbungObj.getInt("id"),
                        cerbungObj.getString("title"),
                        cerbungObj.getString("created_at"),
                        cerbungObj.getString("username"),
                        cerbungObj.getString("description"),
                        cerbungObj.getString("cover_url"),
                        cerbungObj.getString("genre"),
                        cerbungObj.getInt("likeCount"),
                        cerbungObj.getInt("parCount"),
                        isPrivate,
                        isLiked
                    )
                    val paraArray = obj.getJSONArray("paragraphs")
                    for (i in 0 until paraArray.length()) {
                        val paraObj = paraArray.getJSONObject(i)
                        var isLiked = false
                        if (paraObj.getInt("isLiked") == 1) {
                            isLiked = true
                        }

                        val para = Paragraph(
                            paraObj.getInt("id"),
                            paraObj.getInt("cerbung_id"),
                            paraObj.getString("creator"),
                            paraObj.getString("content"),
                            isLiked
                        )
                        paragraphs.add(para)
                    }
                    updateView()
                    updateList()
                }
            },
            Response.ErrorListener {
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["cerbung_id"] = cerbungId.toString()
                params["user_id"] = userId.toString()
                return params
            }
        }
        stringRequest.setShouldCache(false)
        q.add(stringRequest)

        binding.txtNewParagraph.doOnTextChanged { text, start, before, count ->
            var charCount = binding.txtNewParagraph.length()
            binding.txtCharCount.text = "(" + charCount + " of 70 characters)"
        }

        binding.imgLike.setOnClickListener{
            if(!cerbung.is_liked!!) {
                volleyHelper("like")
            }
            else{
                volleyHelper("dislike")
            }
        }

        binding.btnSubmit.setOnClickListener {
//            TODO: Buat Kalo Submit, Ajukannya ntar aja :D, Cek isAllowed to Edit
//            加油
        }

        binding.btnFollow.setOnClickListener {
//            TODO: Follow :D, cek udh foll/blm
        }
    }

    fun updateView(){
        val builder = Picasso.Builder(binding.root.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(cerbung.url).into(binding.imgPoster)

        binding.txtTitle.setText(cerbung.title)
        binding.txtSize.text = cerbung.parCount.toString()
        binding.txtLike.text = cerbung.likeCount.toString()
        binding.txtGenre.text = cerbung.genre

        var access = ""
        if (cerbung.is_private) access = "Restricted" else access = "Public"
        binding.txtAccess.text = access

        binding.txtAuthor.setText(cerbung.author)
        binding.txtDate.setText(cerbung.date)
        binding.txtCharCount.text = "(0 of 70 characters)"

        if(!cerbung.is_liked!!) {
            binding.imgLike.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
        }
        else{
            binding.imgLike.setImageResource(R.drawable.baseline_thumb_up_24)
        }

        if(cerbung.is_private!!) {
            binding.txtNewParagraph.isVisible = false
            binding.txtCharCount.isVisible = false
            binding.btnSubmit.text = "Request Access"
        }
        else{
            binding.txtNewParagraph.isVisible = true
            binding.txtCharCount.isVisible = true
            binding.btnSubmit.text = "Submit"
        }
    }
    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(this)
        var recyclerView = binding.recyclerView
        recyclerView.layoutManager = lm
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ParagraphAdapter(paragraphs, this)
    }

    private fun volleyHelper(action:String){
        val user_id = shared.getInt("user_id",0)
        var q = Volley.newRequestQueue(this)
        var url = ""
        if(action == "like"){
            url = "https://ubaya.me/native/160421119/cerbung_like.php"
        }else{
            url = "https://ubaya.me/native/160421119/cerbung_dislike.php"
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("Volley",it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val message:String = obj.getString("message")
                    if(!cerbung.is_liked!!){
                        cerbung.is_liked = true
                        cerbung.likeCount++
                    }
                    else{
                        cerbung.is_liked = false
                        cerbung.likeCount--
                    }
                    updateView()
                    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this, it.message.toString() , Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["user_id"] = user_id.toString()
                params["cerbung_id"] = cerbung.id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
}