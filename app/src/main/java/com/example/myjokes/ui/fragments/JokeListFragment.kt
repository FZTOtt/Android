package com.example.myjokes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.JokeListFragmentBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel
import com.example.myjokes.ui.joke_list.recycler.adapter.JokeAdapter

class JokeListFragment: Fragment() {
    private val viewModel: JokeListViewModel by activityViewModels()
    private lateinit var binding: JokeListFragmentBinding
    private lateinit var adapter: JokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JokeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JokeAdapter{position ->
            viewModel.setCurrentJokeIndex(position)
            (activity as? MainActivity)?.openJokeDetailFragment()
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.jokes.observe(viewLifecycleOwner) { jokes -> updateJokes(jokes) }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading -> handleVisibility(isLoading) }

        binding.refreshButton.setOnClickListener { viewModel.getJokes() }
        binding.addNew.setOnClickListener {
            (activity as? MainActivity)?.openAddJokeFragment()
        }
    }

    private fun updateJokes(jokes: List<Joke>){
        adapter.setNewData(jokes)
        binding.findedNothing.isVisible = jokes.isEmpty()
        binding.recyclerView.isVisible = jokes.isEmpty()
    }

    private fun handleVisibility (isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.recyclerView.isVisible = !isLoading
        binding.findedNothing.isVisible = !isLoading && binding.findedNothing.isVisible
    }
}