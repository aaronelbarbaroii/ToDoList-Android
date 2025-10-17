package com.example.todolist_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_android.databinding.ItemHeaderBinding

class HeaderAdapter (var title: String) : RecyclerView.Adapter<HeaderViewHolder>() {

        // ¿Cuál es la vista para los elementos?
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
            return HeaderViewHolder(binding)
        }

        // ¿Cuáles son los datos para el elemento?
        override fun onBindViewHolder(holder: com.example.todolist_android.adapters.HeaderViewHolder, position: Int) {
            holder.render(title)
        }

        // ¿Cuántos elementos se quieren listar?
        override fun getItemCount(): Int {
            return 1
        }

}

class HeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(title: String) {
        binding.titleTextView.text = title
    }
}