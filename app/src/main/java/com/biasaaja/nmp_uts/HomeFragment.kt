package com.biasaaja.nmp_uts

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.FragmentHomeBinding
import com.biasaaja.nmp_uts.databinding.FragmentPrefBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class HomeFragment : Fragment() {
    private var cerbungs:ArrayList<Cerbung> = ArrayList()
    private lateinit var binding:FragmentHomeBinding
    private lateinit var shared: SharedPreferences
    private var genre:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = requireActivity().getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCerbungs()

        binding.btnAll.setOnClickListener {
            genre = ""
            getCerbungs()
            updateList()
        }
        binding.btnThriller.setOnClickListener {
            genre = "Thriller"
            getCerbungs()
            updateList()
        }
        binding.btnRomance.setOnClickListener {
            genre = "Romance"
            getCerbungs()
            updateList()
        }
        binding.btnDrama.setOnClickListener {
            genre = "Drama"
            getCerbungs()
            updateList()
        }
        binding.btnComedy.setOnClickListener {
            genre = "Comedy"
            getCerbungs()
            updateList()
        }
        binding.btnAction.setOnClickListener {
            genre = "Action"
            getCerbungs()
            updateList()
        }


    }

    fun getCerbungs(){
        cerbungs.clear()
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421119/cerbung_get.php"

        var stringRequest = @SuppressLint("ShowToast")
        object : StringRequest(Method.POST, url, Response.Listener {
            val obj = JSONObject(it)
            if(obj.getString("result")=="OK") {
                val data = obj.getJSONArray("data")
                for(i in 0 until data.length()){
                    var cerbungObj = data.getJSONObject(i)
                    var isPrivate = false
                    if(cerbungObj.getInt("is_private") == 1){
                        isPrivate = true
                    }
                    val cerbung = Cerbung(cerbungObj.getInt("id"),
                        cerbungObj.getString("title"),
                        cerbungObj.getString("created_at"),
                        cerbungObj.getString("username"),
                        cerbungObj.getString("description"),
                        cerbungObj.getString("cover_url"),
                        cerbungObj.getString("genre"),
                        cerbungObj.getInt("likeCount"),
                        cerbungObj.getInt("parCount"),
                        null,
                        isPrivate,
                        null
                        )
                    cerbungs.add(cerbung)
                }
                updateList()
            }else if(obj.getString("result")=="EMPTY"){
                Snackbar.make(binding.root,obj.getString("message"), Snackbar.LENGTH_LONG).show()
            }
        },
            Response.ErrorListener {
                Snackbar.make(binding.root,it.message.toString(), Snackbar.LENGTH_LONG).show()
            }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["genre"] = genre
                return params
            }
        }
        stringRequest.setShouldCache(false)
        q.add(stringRequest)

    }

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = binding.recyclerViewHome
        recyclerView.layoutManager = lm
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CerbungAdapter(cerbungs, this.activity)
    }

}