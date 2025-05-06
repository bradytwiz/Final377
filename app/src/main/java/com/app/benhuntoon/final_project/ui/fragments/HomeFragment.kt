package com.app.benhuntoon.final_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.benhuntoon.final_project.databinding.FragmentHomeBinding
import com.app.benhuntoon.final_project.R

//create a story object class
data class Story(val title: String, val wordCount: Int, val templateId: String)

//class to initialize stories as displayable
class StoryAdapter(
    private val storyList: List<Story>,
    private val onItemClicked: (Story) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    //initialize the story holder
    class StoryViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val storyTitleTextView: android.widget.TextView = itemView.findViewById(android.R.id.text1)
        val wordCountTextView: android.widget.TextView = itemView.findViewById(android.R.id.text2)

        //bind the story and the holder for proper display
        fun bind(story: Story, onItemClicked: (Story) -> Unit) {
            //set story title and input word count (not total)
            storyTitleTextView.text = story.title
            wordCountTextView.text = "Word Count: ${story.wordCount}"
            itemView.setOnClickListener {
                onItemClicked(story)
            }
        }
    }
    //inflation function for proper layout display
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false) // Replace with your item layout
        return StoryViewHolder(view)
    }
    //binding function for view holder
    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storyList[position]
        holder.bind(story, onItemClicked)
    }
    //function to count size of story list
    override fun getItemCount(): Int {
        return storyList.size
    }
}

//function to handle home screen fragment
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var storyList: MutableList<Story>
    private lateinit var adapter: StoryAdapter

    //inflation function for layout using the view binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //sets up the view with each potential story
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //stories are currently hard coded in and must be manually input through code
        storyList = mutableListOf()
        storyList.add(Story("Finals Week", 14, "finals_week"))
        storyList.add(Story("The Zoo Trip", 8, "zoo_trip"))
        storyList.add(Story("Beach Day", 12,"beach_day"))
        storyList.add(Story("The Weirdest Day", 8, "weirdest_day"))
        storyList.add(Story("Math Class", 9, "math_class"))
        storyList.add(Story("Just Setup the Chairs", 10, "setup_chairs"))
        storyList.add(Story("Free Cake", 5, "free_cake"))
        storyList.add(Story("The Best Burger", 10, "best_burger"))
        storyList.add(Story("Fancy Restaurant", 8, "fancy_restaurant"))
        storyList.add(Story("The Best Movie in the World", 18, "best_movie"))
        storyList.add(Story("Pie Contest", 5, "pie_contest"))
        storyList.add(Story("Guys Night", 12, "guys_night"))
        storyList.add(Story("The Longest Weekend", 13, "longest_weekend"))

        //initialize story adapter for selected story
        adapter = StoryAdapter(storyList) { clickedStory ->
            val createMadLibFragment = CreateMadLibFragment()
            val bundle = Bundle()
            bundle.putString("templateId", clickedStory.templateId) // Pass the templateId
            createMadLibFragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, createMadLibFragment)
                .addToBackStack(null)
                .commit()
        }
        //finish display set up with layout manager and adapter
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter
    }
    //handle view desctruction
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
