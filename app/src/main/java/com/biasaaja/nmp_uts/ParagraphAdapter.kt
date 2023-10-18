package com.biasaaja.nmp_uts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biasaaja.nmp_uts.databinding.ParagraphCardBinding

class ParagraphAdapter(val cerbungId: Int) : RecyclerView.Adapter<ParagraphAdapter.ParagraphViewHolder>() {
    class ParagraphViewHolder(val binding: ParagraphCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder {
        val binding = ParagraphCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ParagraphViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParagraphViewHolder, position: Int) {
        val relatedParagraphes = arrayListOf<Paragraph>()
        for (paragraph in Global.paragraphes) {
            if (paragraph.cerbungId == cerbungId)
            {
                relatedParagraphes.add(paragraph)
            }
        }
        with(holder.binding) {
            txtParagraph.text = relatedParagraphes[position].paragraph

            txtAuthor.text = User(0, "").findUserById(relatedParagraphes[position].authorId).toString()
            //kendala: Constructor berparameter

        }

    }

    override fun getItemCount(): Int {
        var i = 0
        for (paragraph in Global.paragraphes) {
            if (paragraph.cerbungId == cerbungId)
            {
                i++
            }
        }
        return i
    }
}