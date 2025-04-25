package com.example.finalxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class HomeFragment : Fragment() {

    private lateinit var storiesRecyclerView: RecyclerView
    private lateinit var storyList: MutableList<MainActivity.Story>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        storiesRecyclerView = view.findViewById(R.id.storiesRecyclerView)
        storiesRecyclerView.layoutManager = LinearLayoutManager(context)

        storyList = mutableListOf()
        storyList.add(MainActivity.Story("Funny Adventure", 5))
        storyList.add(MainActivity.Story("The Zoo Trip", 8))
        storyList.add(MainActivity.Story("A Day at the Beach", 10))

        val adapter = MainActivity().StoryAdapter(storyList)
        storiesRecyclerView.adapter = adapter

        return view
    }
}