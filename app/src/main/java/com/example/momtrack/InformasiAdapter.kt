package com.example.momtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InformasiAdapter(
    private val listInfo: List<Informasi>,
    private val onItemClick: (Informasi) -> Unit
) : RecyclerView.Adapter<InformasiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgInfo)
        val judul: TextView = view.findViewById(R.id.tvJudul)
        val deskripsi: TextView = view.findViewById(R.id.tvDeskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_informasi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listInfo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = listInfo[position]
        holder.img.setImageResource(info.gambar)
        holder.judul.text = info.judul
        holder.deskripsi.text = info.deskripsi
        holder.itemView.setOnClickListener {
            onItemClick(info)
        }
    }
}