package com.app.benhuntoon.final_project.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.ActivitySavedMadlibsBinding
import com.app.benhuntoon.final_project.ui.fragments.HomeFragment
import com.app.benhuntoon.final_project.ui.fragments.SavedMadLibsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SavedMadLibsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedMadlibsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMadlibsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(SavedMadLibsFragment())
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewSaved)

        //set the "Saved" item as selected initially
        bottomNavigationView.selectedItemId = R.id.saved

        //handle item selection in the BottomNavigationView for navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.saved -> {

                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}