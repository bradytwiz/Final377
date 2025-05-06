package com.app.benhuntoon.final_project.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.ActivityMainBinding
import com.app.benhuntoon.final_project.ui.fragments.HomeFragment
import com.app.benhuntoon.final_project.ui.fragments.SavedMadLibsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    //set up the replace fragment function
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    //create the home fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set the default screen to home
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        //set up nav bar listener with navigation mapping
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.saved -> replaceFragment(SavedMadLibsFragment())
                // this is where error screens and potentially other windows could be implemented
                else -> {

                }
            }
            //return successful screen swap
            true
        }
    }
}