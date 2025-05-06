package com.app.benhuntoon.final_project.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.data.database.MadLibEntity

//set up adapter class for madlibs
class MadLibsAdapter(private val onDeleteClick: (MadLibEntity) -> Unit) :
    ListAdapter<MadLibEntity, MadLibsAdapter.MadLibViewHolder>(MadLibDiffCallback()) {

        // create the view holder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MadLibViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_madlib, parent, false)
        return MadLibViewHolder(view)
    }
    //bind the data to whichever index is needed
    override fun onBindViewHolder(holder: MadLibViewHolder, position: Int) {
        val madLib = getItem(position)
        holder.bind(madLib)
    }

    //binds madlibs data to displayable entity
    inner class MadLibViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_madlib_title)
        private val storyTextView: TextView = itemView.findViewById(R.id.tv_madlib_story)
        private val deleteButton: View = itemView.findViewById(R.id.btn_delete)

        //binding function for display
        fun bind(madLib: MadLibEntity) {
            titleTextView.text = madLib.title
            storyTextView.text = madLib.story
            deleteButton.setOnClickListener { onDeleteClick(madLib) }
        }
    }
}

//class to handle changes in saved madlibs
class MadLibDiffCallback : DiffUtil.ItemCallback<MadLibEntity>() {
    //function to verify if a change has occurred
    override fun areItemsTheSame(oldItem: MadLibEntity, newItem: MadLibEntity): Boolean {
        return oldItem.id == newItem.id
    }

    //updater function
    override fun areContentsTheSame(oldItem: MadLibEntity, newItem: MadLibEntity): Boolean {
        return oldItem == newItem
    }
}