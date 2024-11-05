package com.example.myjokes.ui.joke_list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.data.Joke
import com.example.myjokes.databinding.ActivityJokesListBinding
import com.example.myjokes.ui.joke_details.JokeDetailsActivity
import com.example.myjokes.ui.joke_list.recycler.adapter.JokeAdapter

class JokesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokesListBinding
    private lateinit var viewModel: JokeListViewModel

    private val adapter = JokeAdapter {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        initViewModel()
        viewModel.generateJokes()
    }

    private fun initViewModel() {
        val factory = JokesViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[JokeListViewModel::class.java]

        viewModel.jokes.observe(this) { adapter.setNewData(it) }
        viewModel.error.observe(this) { showError(it) }
    }

    private fun showError(it: String?) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}