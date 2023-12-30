package com.biasaaja.nmp_uts

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.biasaaja.nmp_uts.databinding.CerbungCardBinding
import com.biasaaja.nmp_uts.databinding.CerbungFollowCardBinding
import com.squareup.picasso.Picasso

class FollowAdapter(val cerbungs:ArrayList<Cerbung>, val context: FragmentActivity?) : RecyclerView.Adapter<FollowAdapter.CerbungViewHolder>() {

    class CerbungViewHolder(val binding: CerbungFollowCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowAdapter.CerbungViewHolder {
        val binding = CerbungFollowCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowAdapter.CerbungViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: FollowAdapter.CerbungViewHolder, position: Int) {
        val url = cerbungs[position].url
        with(holder.binding) {
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            Picasso.get().load(url).into(imgPosterFollow)
            txtTitleFollow.text = cerbungs[position].title
            txtAuthorFollow.text = "by " + cerbungs[position].author.toString()
            txtLastUp.text = "Last update: "+cerbungs[position].lastUpdated.toString()
        }

        holder.binding.cardView.setOnClickListener {
            val intent = Intent(it.context, ReadActivity::class.java)
            intent.putExtra(ReadActivity.ID_CERBUNG, cerbungs[position].id)
            it.context.startActivity(intent)
        }

    }
}