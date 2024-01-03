package com.biasaaja.nmp_uts

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.FragmentFollowingBinding
import com.biasaaja.nmp_uts.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class FollowingFragment : Fragment() {

    private var cerbungs:ArrayList<Cerbung> = ArrayList()
    private lateinit var binding: FragmentFollowingBinding
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
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCerbungs()
    }

    override fun onResume() {
        super.onResume()
        getCerbungs()
    }

    fun getCerbungs(){
        cerbungs.clear()
        val q = Volley.newRequestQueue(activity)
        val userId = shared.getInt("user_id",0)
        val url = "https://ubaya.me/native/160421119/cerbung_getfollowed.php"

        var stringRequest = @SuppressLint("ShowToast")
        object : StringRequest(
            Method.POST, url, Response.Listener {
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
                        cerbungObj.getString("lastUpdate"),
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
                params["user_id"] = userId.toString()
                return params
            }
        }
        stringRequest.setShouldCache(false)
        q.add(stringRequest)
    }

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = binding.recyclerViewFollowing
        recyclerView.layoutManager = lm
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = FollowAdapter(cerbungs, this.activity)
    }
}