package com.app.benhuntoon.final_project.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.ActivityMainBinding
import com.app.benhuntoon.final_project.ui.fragments.HomeFragment
import com.app.benhuntoon.final_project.ui.fragments.SavedMadLibsFragment
import com.app.benhuntoon.final_project.ui.fragments.saved


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.saved -> replaceFragment(SavedMadLibsFragment())
                //R.id.saved -> replaceFragment(saved())

                else -> {

                }
            }
            true
        }
    }
}