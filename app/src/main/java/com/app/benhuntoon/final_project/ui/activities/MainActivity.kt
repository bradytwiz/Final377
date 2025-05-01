package com.app.benhuntoon.final_project.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.ActivityMainBinding
import com.app.benhuntoon.final_project.ui.fragments.Home
import com.app.benhuntoon.final_project.ui.fragments.saved
import com.app.benhuntoon.final_project.ui.fragments.CreateMadLibFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.saved -> replaceFragment(saved())

                //where to send users for other pages
                else ->{

                }
            }
            true
        }

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, CreateMadLibFragment())
//                .commit()
//        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}