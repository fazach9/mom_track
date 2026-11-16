package com.example.momtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(
    private val reminders: MutableList<Reminder>,
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (Int) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvReminderTime)
        val tvDescription: TextView = view.findViewById(R.id.tvReminderDesc)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDeleteReminder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.tvTime.text = reminder.time
        holder.tvDescription.text = reminder.description
        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
        holder.itemView.setOnClickListener {
            onEditClick(position)
        }
    }

    override fun getItemCount(): Int = reminders.size
}