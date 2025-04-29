package com.app.benhuntoon.final_project.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.ui.fragments.CreateMadLibFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CreateMadLibFragment())
                .commit()
        }
    }
}