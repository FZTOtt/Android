package com.example.myjokes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myjokes.databinding.AddJokeFragmentBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel

class AddJokeFragment: Fragment() {
    private val viewModel: JokeListViewModel by activityViewModels()
    private lateinit var binding: AddJokeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddJokeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputCategory = binding.inputCategory
        val inputQuestion = binding.inputQuestion
        val inputAnswer = binding.inputAnswer

        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.addJokeButton.setOnClickListener {
            viewModel.addJoke(inputCategory.text.toString(),
                inputQuestion.text.toString(),
                inputAnswer.text.toString(),
                true)
            parentFragmentManager.popBackStack()
        }

    }
}