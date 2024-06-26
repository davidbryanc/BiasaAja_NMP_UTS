package com.biasaaja.nmp_uts

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.biasaaja.nmp_uts.databinding.ActivityCreate1Binding

class Create1Activity : AppCompatActivity() {
    private  lateinit var  binding: ActivityCreate1Binding
    private var firstPar:String = ""
    private var access:String = "Restricted"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreate1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinGenre.adapter = adapter

        // Set text ketika user kembali dari halaman Create-2
        firstPar = intent.getStringExtra(Create3Activity.KEY_PAR) ?: ""
        access = intent.getStringExtra(Create3Activity.KEY_ACC) ?: "Restricted"
        binding.txtImg.setText(intent.getStringExtra(Create3Activity.KEY_IMG))
        binding.txtDescription.setText(intent.getStringExtra(Create3Activity.KEY_DESC))
        binding.txtTitle.setText(intent.getStringExtra(Create3Activity.KEY_TITLE))
        binding.spinGenre.setSelection(getIndex(intent.getStringExtra(Create3Activity.KEY_GENRE).toString()))

        // Menuju halaman Create-2
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, Create2Activity::class.java)
            intent.putExtra(Create3Activity.KEY_TITLE, binding.txtTitle.text.toString())
            intent.putExtra(Create3Activity.KEY_DESC, binding.txtDescription.text.toString())
            intent.putExtra(Create3Activity.KEY_IMG, binding.txtImg.text.toString())
            intent.putExtra(Create3Activity.KEY_GENRE, binding.spinGenre.selectedItem.toString())
            intent.putExtra(Create3Activity.KEY_PAR, firstPar)
            intent.putExtra(Create3Activity.KEY_ACC, access)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun getIndex(s:String):Int{
        for (i in 0..binding.spinGenre.count - 1){
            if(binding.spinGenre.getItemAtPosition(i).toString() == s){
                return i
            }
        }
        return 0
    }
}