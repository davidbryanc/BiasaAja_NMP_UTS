package com.biasaaja.nmp_uts

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.biasaaja.nmp_uts.databinding.ParagraphCardBinding
import org.json.JSONObject

class ParagraphAdapter(val paragraphs: ArrayList<Paragraph>, val context: Activity?) : RecyclerView.Adapter<ParagraphAdapter.ParagraphViewHolder>() {
    class ParagraphViewHolder(val binding: ParagraphCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder {
        val binding = ParagraphCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ParagraphViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParagraphViewHolder, position: Int) {
        with(holder.binding) {
            txtParagraph.text = paragraphs[position].paragraph
            txtAuthor.text = paragraphs[position].author
            if(!paragraphs[position].is_liked){
                btnLike.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
            }
            else{
                btnLike.setImageResource(R.drawable.baseline_thumb_up_24)
            }
        }

        holder.binding.btnLike.setOnClickListener {
            if(!paragraphs[position].is_liked){
                volleyHelper(paragraphs[position].id, holder,position,"like")
            }
            else{
                volleyHelper(paragraphs[position].id, holder,position,"dislike")
            }
        }
    }

    override fun getItemCount(): Int {
        return paragraphs.size
    }

    private fun volleyHelper(paraId:Int, holder: ParagraphViewHolder, position:Int, action:String){
        val shared = context!!.getSharedPreferences(Global.sharedFile, Context.MODE_PRIVATE)
        val user_id = shared.getInt("user_id",0)
        var q = Volley.newRequestQueue(this.context)
        var url = ""
        if(action == "like"){
            url = "https://ubaya.me/native/160421119/paragraph_like.php"
        }else{
            url = "https://ubaya.me/native/160421119/paragraph_dislike.php"
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("Volley",it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val message:String = obj.getString("message")
                    if(!paragraphs[position].is_liked){
                        paragraphs[position].is_liked = true
                        holder.binding.btnLike.setImageResource(R.drawable.baseline_thumb_up_24)
                    }
                    else{
                        paragraphs[position].is_liked = true
                        holder.binding.btnLike.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                    }
                    Toast.makeText(this.context, message , Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this.context, it.message.toString() , Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["user_id"] = user_id.toString()
                params["para_id"] = paraId.toString()
                return params
            }
        }
        q.add(stringRequest)
    }


}