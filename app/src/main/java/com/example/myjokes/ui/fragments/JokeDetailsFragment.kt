package com.example.myjokes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myjokes.databinding.JokeDetailsFragmentBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel

class JokeDetailsFragment: Fragment() {
    private val viewModel: JokeListViewModel by activityViewModels()
    private lateinit var binding: JokeDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JokeDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentJoke.observe(viewLifecycleOwner) { joke ->
            joke?.let {
                binding.jokeCategory.text = it.category
                binding.jokeQuestion.text = it.question
                binding.jokeAnswer.text = it.answer
            }
        }

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}