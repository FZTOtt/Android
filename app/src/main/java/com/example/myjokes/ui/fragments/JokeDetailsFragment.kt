package com.example.myjokes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myjokes.R
import com.example.myjokes.databinding.JokeDetailsFragmentBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel

class JokeDetailsFragment: Fragment(R.layout.joke_details_fragment) {
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

        viewModel.currentJokeIndex.observe(viewLifecycleOwner) { index ->
            viewModel.jokes.value?.get(index)?.let { joke ->
                binding.jokeCategory.text = joke.category
                binding.jokeQuestion.text = joke.question
                binding.jokeAnswer.text = joke.answer
            }
        }
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}