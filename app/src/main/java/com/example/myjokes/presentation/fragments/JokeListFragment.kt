package com.example.myjokes.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjokes.domain.entity.ErrorType
import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.databinding.JokeListFragmentBinding
import com.example.myjokes.presentation.joke_list.JokeListViewModel
import com.example.myjokes.presentation.joke_list.recycler.adapter.JokeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JokeListFragment: Fragment() {
    private val viewModel: JokeListViewModel by activityViewModels()
    private lateinit var binding: JokeListFragmentBinding
    private lateinit var adapter: JokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.jokesFlow.collectLatest { jokes ->
                    println("collectLatest")
                    println(jokes)
                    updateJokes(jokes)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading -> handleVisibility(isLoading) }

        viewModel.error.observe(viewLifecycleOwner) { error -> handleError(error) }

        binding.refreshButton.setOnClickListener {
            viewModel.loadAllJokes()
        }
        binding.addNew.setOnClickListener {
            (activity as? MainActivity)?.openAddJokeFragment()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
                    val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                    val pastVisibleItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        viewModel.getJokes(false)
                    }
                }
            }
        })

        viewModel.loadAllJokes()
    }

    private fun handleError(error: ErrorType) {
        when (error) {
            ErrorType.NONE -> {

            }

            ErrorType.CONNECTION_ERROR -> {
                Toast.makeText(
                    requireContext(),
                    "Интернет соединение не установлено",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateJokes(jokes: List<Joke>){
        println("to UI")
        println(jokes)
        adapter.setNewData(jokes)
        binding.findedNothing.isVisible = jokes.isEmpty()
        binding.recyclerView.isVisible = jokes.isNotEmpty()
    }

    private fun handleVisibility (isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.recyclerView.isVisible = !isLoading
        binding.findedNothing.isVisible = !isLoading && binding.findedNothing.isVisible
    }
}