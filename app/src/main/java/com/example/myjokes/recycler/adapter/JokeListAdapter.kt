package com.example.myjokes.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.JokeItemBinding
import com.example.myjokes.recycler.JokeViewHolder
import com.example.myjokes.recycler.util.JokeDiffUtilCallback
import com.example.myjokes.recycler.util.JokeItemCallback

class JokeListAdapter(itemCallback: JokeItemCallback):
    ListAdapter<Joke, JokeViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}