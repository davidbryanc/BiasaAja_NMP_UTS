package com.biasaaja.nmp_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.biasaaja.nmp_uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val fragments: ArrayList<Fragment> = ArrayList()

    companion object{
        val KEY_USERNAME = "USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragments.add(HomeFragment())
        fragments.add(FollowingFragment())
        fragments.add(CreateFragment())
        fragments.add(UserFragment())
        fragments.add(PrefFragment())

        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.botNav.selectedItemId = binding.botNav.menu.getItem(position).itemId
            }
        })
        binding.botNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemFollow -> 1
                R.id.itemCreate -> 2
                R.id.itemUser -> 3
                R.id.itemPref -> 4
                else -> 0
            }
            true
        }
    }
}