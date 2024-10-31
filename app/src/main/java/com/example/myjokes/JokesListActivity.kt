package com.example.myjokes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.data.JokeGenerator
import com.example.myjokes.databinding.ActivityJokesListBinding
import com.example.myjokes.recycler.adapter.JokeAdapter
import com.example.myjokes.recycler.adapter.JokeListAdapter
import com.example.myjokes.recycler.util.JokeItemCallback

class JokesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokesListBinding

    private val itemCallback = JokeItemCallback()
    private val adapter = JokeListAdapter(itemCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        val generator = JokeGenerator()
        adapter.submitList(generator.generateJokeData())
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}