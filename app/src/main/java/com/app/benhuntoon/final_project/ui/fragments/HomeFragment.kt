package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var storyList: MutableList<Story>
    private lateinit var adapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyList = mutableListOf()
        storyList.add(Story("Funny Adventure", 5))
        storyList.add(Story("The Zoo Trip", 8))
        storyList.add(Story("A Day at the Beach", 10))

        adapter = StoryAdapter(storyList) // Create the adapter with the storyList
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
