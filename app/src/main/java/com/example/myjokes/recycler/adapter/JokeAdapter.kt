package com.example.myjokes.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.JokeItemBinding
import com.example.myjokes.recycler.JokeViewHolder
import com.example.myjokes.recycler.util.JokeDiffUtilCallback

class JokeAdapter: RecyclerView.Adapter<JokeViewHolder>() {

    private var data = emptyList<Joke>()

    fun setNewData(newData: List<Joke>) {
        val diffUtilCallback = JokeDiffUtilCallback(data, newData)
        val calculatedDiff = DiffUtil.calculateDiff(diffUtilCallback)
        data = newData
        calculatedDiff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(data[position])
    }
}