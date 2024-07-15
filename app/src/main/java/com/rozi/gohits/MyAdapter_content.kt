package com.rozi.gohits

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter_content(private val menuItems: List<Menuconten>) : RecyclerView.Adapter<MyAdapter_content.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        val context = holder.itemView.context

        // URL dasar untuk gambar
        val baseUrl = "https://gohit.id/assets/image/"
        val imageUrl = baseUrl + menuItem.img // Menggabungkan URL dasar dengan nama file gambar

        // Gunakan Glide untuk memuat gambar
        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.titleTextView.text = menuItem.judul
        holder.auth.text = menuItem.aut
        holder.price.text = menuItem.price
        holder.itemView.setOnClickListener {
            try {
                // Toast.makeText(holder.itemView.context, "NAMA: ${menuItem.judul} ", Toast.LENGTH_SHORT).show()
                // val context = holder.itemView.context
                // val intent = Intent(context, detail::class.java)
                // intent.putExtra("id", menuItem.id)
                // context.startActivity(intent)

            } catch (e: Exception) {
                // Handle the exception here
                Log.e("Exception", "Error occurred: ${e.message}")
                Toast.makeText(context, "Error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun getItemCount(): Int {
        return menuItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val auth: TextView = itemView.findViewById(R.id.authorTextView)
        val price: TextView = itemView.findViewById(R.id.priceTextView)
    }
}
