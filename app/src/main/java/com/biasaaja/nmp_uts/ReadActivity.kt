package com.biasaaja.nmp_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.biasaaja.nmp_uts.databinding.ActivityHomeBinding
import com.biasaaja.nmp_uts.databinding.ActivityReadBinding
import com.squareup.picasso.Picasso

class ReadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadBinding

    companion object{
        val ID_CERBUNG = "ID_CERBUNG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cerbungId = intent.getIntExtra(ID_CERBUNG, 0)

        val cerbung= Cerbung(0,"","","", "", "", 0, false).findCerbungById(cerbungId)
        val author = User("", "", "").findUserById(cerbung!!.author)
        val genre = Genre(0, "").findGenreById(cerbung.genre)

        val lm: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = ParagraphAdapter(cerbungId)

        val builder = Picasso.Builder(binding.root.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(cerbung.url).into(binding.imgPoster)

//        binding.txtTitle.text = cerbungId.toString()
        binding.txtTitle.setText(cerbung.title)

        var size = 0
        for (paragraph in Global.paragraphes)
        {
            if(paragraph.cerbung == cerbungId) size++
        }
        binding.txtSize.text = size.toString()

        var like = 0 //Nanti akan diganti dengan jumlah like-nya yang sebenarnya
        binding.txtLike.text = like.toString()
        binding.txtGenre.text = genre!!.name

        var access = ""
        if (cerbung.access) access = "Public" else access = "Restricted"
        binding.txtAccess.text = access

        binding.txtAuthor.setText(author.toString())
        binding.txtDate.setText(cerbung.date)
        binding.txtCharCount.text = "(0 of 70 characters)"

        binding.txtNewParagraph.doOnTextChanged { text, start, before, count ->
            var charCount = binding.txtNewParagraph.length()
            binding.txtCharCount.text = "(" + charCount + " of 70 characters)"
        }

//        Log.d("txtTitle", cerbung.title)
//        Log.d("txtAuthor", author.toString())
//        Log.d("txtDate", cerbung.date)
    }
}