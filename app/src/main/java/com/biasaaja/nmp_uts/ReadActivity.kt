package com.biasaaja.nmp_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val cerbung= Cerbung(0,"","",0, "", "", 0, false).findCerbungById(cerbungId)
        val author = User(0, "").findUserById(cerbung!!.author)

        val lm: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = ParagraphAdapter(cerbungId)

        val builder = Picasso.Builder(binding.root.context)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        Picasso.get().load(cerbung.url).into(binding.imgPoster)

//        binding.txtTitle.text = cerbungId.toString()
        binding.txtTitle.setText(cerbung.title)
        binding.txtAuthor.setText(author.toString())
//        binding.txtAuthor.text  = cerbung.id.toString()
        binding.txtDate.setText(cerbung.date)
        Log.d("txtTitle", cerbung.title)
        Log.d("txtAuthor", author.toString())
        Log.d("txtDate", cerbung.date)
    }
}