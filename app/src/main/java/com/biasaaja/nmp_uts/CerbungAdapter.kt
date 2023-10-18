package com.biasaaja.nmp_uts

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biasaaja.nmp_uts.databinding.ActivityReadBinding
import com.biasaaja.nmp_uts.databinding.CerbungCardBinding
import com.squareup.picasso.Picasso

class CerbungAdapter() : RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {

    companion object{
        val ID_CERBUNG = "ID_CERBUNG"
    }

    class CerbungViewHolder(val binding: CerbungCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = Global.cerbungs[position].url
        with(holder.binding) {
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            Picasso.get().load(url).into(imgPoster)
            txtTitle.text = Global.cerbungs[position].title

            txtAuthor.text = User(0, "").findUserById(Global.cerbungs[position].author).toString()
            //kendala: Constructor berparameter

            txtDescription.text = Global.cerbungs[position].description

            btnRead.setOnClickListener{
                val intent = Intent(it.context, ReadActivity::class.java)
                intent.putExtra(ID_CERBUNG, Global.cerbungs[position].id)
                it.context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return Global.cerbungs.size
    }
}