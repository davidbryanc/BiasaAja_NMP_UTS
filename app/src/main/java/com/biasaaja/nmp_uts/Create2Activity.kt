package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityCreate2Binding

class Create2Activity : AppCompatActivity() {
    private  lateinit var  binding: ActivityCreate2Binding

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
        binding = ActivityCreate2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtParagraph.setText(intent.getStringExtra(Create3Activity.KEY_PAR))

        if(intent.getStringExtra(Create3Activity.KEY_ACC) == "Public"){
            binding.rbPublic.isChecked = true
        }

        binding.btnNext.setOnClickListener {
            val intent2 = Intent(this, Create3Activity::class.java)
            intent2.putExtra(KEY_TITLE, intent.getStringExtra(Create1Activity.KEY_TITLE))
            intent2.putExtra(KEY_DESC, intent.getStringExtra(Create1Activity.KEY_DESC))
            intent2.putExtra(KEY_IMG, intent.getStringExtra(Create1Activity.KEY_IMG))
            intent2.putExtra(KEY_GENRE, intent.getStringExtra(Create1Activity.KEY_GENRE))

            val id = binding.groupAcc.checkedRadioButtonId
            val rb = findViewById<RadioButton>(id)

            intent2.putExtra(KEY_ACC, rb.text.toString())
            intent2.putExtra(KEY_PAR, binding.txtParagraph.text.toString())

            startActivity(intent2)
        }

        binding.btnPrev.setOnClickListener {
            val intent2 = Intent(this, Create1Activity::class.java)
            intent2.putExtra(KEY_TITLE, intent.getStringExtra(Create3Activity.KEY_TITLE))
            intent2.putExtra(KEY_DESC, intent.getStringExtra(Create3Activity.KEY_DESC))
            intent2.putExtra(KEY_IMG, intent.getStringExtra(Create3Activity.KEY_IMG))
            intent2.putExtra(KEY_GENRE, intent.getStringExtra(Create3Activity.KEY_GENRE))
            intent2.putExtra(KEY_ACC, intent.getStringExtra(Create3Activity.KEY_ACC))
            intent2.putExtra(KEY_PAR, intent.getStringExtra(Create3Activity.KEY_PAR))

            startActivity(intent2)
        }
    }
}