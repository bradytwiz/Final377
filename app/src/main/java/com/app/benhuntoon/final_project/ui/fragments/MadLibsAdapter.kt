package com.app.benhuntoon.final_project.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.benhuntoon.final_project.R
import com.app.benhuntoon.final_project.data.database.MadLibEntity

class MadLibsAdapter(
    private val onMadLibClicked: (MadLibEntity) -> Unit,
    private val onDeleteClicked: (MadLibEntity) -> Unit
) : ListAdapter<MadLibEntity, MadLibsAdapter.MadLibViewHolder>(MadLibDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MadLibViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_madlib, parent, false)
        return MadLibViewHolder(view)
    }

    override fun onBindViewHolder(holder: MadLibViewHolder, position: Int) {
        holder.bind(getItem(position), onMadLibClicked, onDeleteClicked)
    }

    inner class MadLibViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_madlib_title)
        private val deleteButton: Button = itemView.findViewById(R.id.btn_delete)

        fun bind(
            madLib: MadLibEntity,
            onMadLibClicked: (MadLibEntity) -> Unit,
            onDeleteClicked: (MadLibEntity) -> Unit
        ) {
            titleTextView.text = madLib.title

            itemView.setOnClickListener {
                onMadLibClicked(madLib)
            }

            deleteButton.setOnClickListener {
                onDeleteClicked(madLib)
            }
        }
    }
}

class MadLibDiffCallback : DiffUtil.ItemCallback<MadLibEntity>() {
    override fun areItemsTheSame(oldItem: MadLibEntity, newItem: MadLibEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MadLibEntity, newItem: MadLibEntity) =
        oldItem == newItem
}
