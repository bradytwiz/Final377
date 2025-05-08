package com.app.benhuntoon.final_project.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.benhuntoon.final_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MadLibDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_madlib_detail)

        val titleTextView = findViewById<TextView>(R.id.titleText)
        val contentTextView = findViewById<TextView>(R.id.contentText)

        val title = intent.getStringExtra("EXTRA_TITLE")
        val content = intent.getStringExtra("EXTRA_CONTENT")

        titleTextView.text = title
        contentTextView.text = content

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewDetail)

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
                    startActivity(Intent(this, SavedMadLibsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}