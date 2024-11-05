package com.example.myjokes.ui.joke_list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.JokeItemBinding

class JokeViewHolder(private val binding: JokeItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke, onClick: (Int) -> Unit){
        binding.category.text = joke.category
        binding.question.text = joke.question
        binding.answer.text = joke.answer

        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }
}