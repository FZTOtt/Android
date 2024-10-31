package com.example.myjokes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.data.JokeGenerator
import com.example.myjokes.databinding.ActivityJokesListBinding
import com.example.myjokes.databinding.JokeItemBinding
import com.example.myjokes.recycler.JokeAdapter

class JokesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokesListBinding

    private val adapter = JokeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        val generator = JokeGenerator()
        adapter.setNewData(generator.generateJokeData())
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}