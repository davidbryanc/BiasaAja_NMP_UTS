package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityCreate3Binding

class Create3Activity : AppCompatActivity() {
    private  lateinit var  binding: ActivityCreate3Binding
    companion object{
        val KEY_TITLE = "TITLE"
        val KEY_DESC = "DESC"
        val KEY_IMG = "IMG"
        val KEY_GENRE = "GENRE"
        val KEY_ACC = "ACC"
        val KEY_PAR = "PAR"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreate3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblAccess.text = intent.getStringExtra(Create2Activity.KEY_ACC)
        binding.lblDescription.text = intent.getStringExtra(Create2Activity.KEY_DESC)
        binding.lblGenre.text = intent.getStringExtra(Create2Activity.KEY_GENRE)
        binding.lblTitle.text = intent.getStringExtra(Create2Activity.KEY_TITLE)
        binding.lblParagraph.text = intent.getStringExtra(Create2Activity.KEY_PAR)

        binding.btnPrev.setOnClickListener {
            val intent2 = Intent(this, Create2Activity::class.java)
            intent2.putExtra(KEY_TITLE, intent.getStringExtra(Create2Activity.KEY_TITLE))
            intent2.putExtra(KEY_DESC, intent.getStringExtra(Create2Activity.KEY_DESC))
            intent2.putExtra(KEY_IMG, intent.getStringExtra(Create2Activity.KEY_IMG))
            intent2.putExtra(KEY_GENRE, intent.getStringExtra(Create2Activity.KEY_GENRE))
            intent2.putExtra(KEY_ACC, intent.getStringExtra(Create2Activity.KEY_ACC))
            intent2.putExtra(KEY_PAR, intent.getStringExtra(Create2Activity.KEY_PAR))

            startActivity(intent2)
        }

        binding.btnPublish.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage("Are you sure about publishing this story?")
            builder.setNegativeButton("CANCEL", null)
            builder.setPositiveButton("PUBLISH") { dialogInterface, i ->
                Toast.makeText(it.context, "Story have been published", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            builder.create().show()
        }
    }
}