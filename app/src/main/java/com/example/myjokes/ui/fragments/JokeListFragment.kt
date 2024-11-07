package com.example.myjokes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.R
import com.example.myjokes.databinding.JokeListFragmentBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel
import com.example.myjokes.ui.joke_list.recycler.adapter.JokeAdapter

class JokeListFragment: Fragment(R.layout.joke_list_fragment) {
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

        viewModel.jokes.observe(viewLifecycleOwner) {adapter.setNewData(it)}
    }
}