package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.benhuntoon.final_project.databinding.FragmentHomeBinding
import com.app.benhuntoon.final_project.R

data class Story(val title: String, val wordCount: Int)

class StoryAdapter(
    private val storyList: List<Story>,
    private val onItemClicked: (Story) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val storyTitleTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val wordCountTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)

        fun bind(story: Story, onItemClicked: (Story) -> Unit) {
            storyTitleTextView.text = story.title
            wordCountTextView.text = "Word Count: ${story.wordCount}"
            itemView.setOnClickListener {
                onItemClicked(story)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false) // Replace with your item layout
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storyList[position]
        holder.bind(story, onItemClicked)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }
}


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var storyList: MutableList<Story>
    private lateinit var adapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyList = mutableListOf()
        storyList.add(Story("Finals Week", 14))
        storyList.add(Story("The Zoo Trip", 8))
        storyList.add(Story("A Day at the Beach", 10))
        storyList.add(Story("The Weirdest Day", 8))
        storyList.add(Story("Math Class", 9))
        storyList.add(Story("Just Setup the Chairs", 11))
        storyList.add(Story("Free Cake", 5))
        storyList.add(Story("But I have a Receipt", 17))
        storyList.add(Story("The Best Burger", 10))
        storyList.add(Story("Fancy Restaurant", 7))

        adapter = StoryAdapter(storyList) { clickedStory: Story ->
            val createMadLibFragment = CreateMadLibFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, createMadLibFragment)
                .addToBackStack(null)
                .commit()

            // Optional: Pass data to CreateMadLibFragment using arguments
            // val bundle = Bundle()
            // bundle.putString("storyTitle", clickedStory.title)
            // createMadLibFragment.arguments = bundle
        }
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
