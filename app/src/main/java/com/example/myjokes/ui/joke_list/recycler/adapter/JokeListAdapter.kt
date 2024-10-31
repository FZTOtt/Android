package com.example.myjokes.ui.joke_list.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.JokeItemBinding
import com.example.myjokes.ui.joke_list.recycler.JokeViewHolder
import com.example.myjokes.ui.joke_list.recycler.util.JokeItemCallback

class JokeListAdapter(
    itemCallback: JokeItemCallback,
    private val clickListener: (Int) -> Unit
):
    ListAdapter<Joke, JokeViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding).apply{
            binding.root.setOnClickListener {
                handlePersonClick(adapterPosition)
            }
        }
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun handlePersonClick(position: Int) {
        clickListener(position)
    }
}