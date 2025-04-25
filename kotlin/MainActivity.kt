package com.example.finalxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var storiesRecyclerView: RecyclerView
    private lateinit var storyList: MutableList<Story>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storiesRecyclerView = findViewById(R.id.storiesRecyclerView)
        storiesRecyclerView.layoutManager = LinearLayoutManager(this)

        storyList = mutableListOf()
        storyList.add(Story("Funny Adventure", 5))
        storyList.add(Story("The Zoo Trip", 8))
        storyList.add(Story("A Day at the Beach", 10))
        val adapter = StoryAdapter(storyList)
        storiesRecyclerView.adapter = adapter
    }

    data class Story(val title: String, val wordCount: Int)

    inner class StoryAdapter(private val storyList: List<Story>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
            return StoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
            val story = storyList[position]
            holder.storyTitleTextView.text = story.title
            holder.wordCountTextView.text = "Word Count: ${story.wordCount}"
        }

        override fun getItemCount(): Int {
            return storyList.size
        }

        inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val storyTitleTextView: TextView = itemView.findViewById(R.id.storyTitleTextView)
            val wordCountTextView: TextView = itemView.findViewById(R.id.wordCountTextView)

        }
    }
}