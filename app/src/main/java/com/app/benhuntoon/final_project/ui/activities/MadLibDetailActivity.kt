// MadLibDetailActivity.kt
package com.app.benhuntoon.final_project.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.benhuntoon.final_project.databinding.ActivityMadlibDetailBinding

class MadLibDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMadlibDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMadlibDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Read extras:
        val title = intent.getStringExtra("EXTRA_TITLE")
        val content = intent.getStringExtra("EXTRA_CONTENT")
        binding.titleText.text   = title
        binding.contentText.text = content
    }
}
