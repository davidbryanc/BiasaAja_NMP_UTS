package com.biasaaja.nmp_uts

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.biasaaja.nmp_uts.databinding.ActivityReadBinding
import com.biasaaja.nmp_uts.databinding.CerbungCardBinding
import com.squareup.picasso.Picasso

class CerbungAdapter(val cerbungs:ArrayList<Cerbung>, val context: FragmentActivity?) : RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {

    class CerbungViewHolder(val binding: CerbungCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = cerbungs[position].url
        with(holder.binding) {
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            Picasso.get().load(url).into(imgPoster)
            txtTitle.text = cerbungs[position].title
            txtAuthor.text = "by " + cerbungs[position].author.toString()
            txtDescription.text = cerbungs[position].description

            txtSize.text = cerbungs[position].parCount.toString()
            txtLike.text = cerbungs[position].likeCount.toString()

            btnRead.setOnClickListener {
                val intent = Intent(it.context, ReadActivity::class.java)
                intent.putExtra(ReadActivity.ID_CERBUNG, cerbungs[position].id)
                it.context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }
}