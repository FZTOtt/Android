package com.example.myjokes.ui.joke_list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeGenerator
import com.example.myjokes.databinding.ActivityJokesListBinding
import com.example.myjokes.ui.joke_details.JokeDetailsActivity
import com.example.myjokes.ui.joke_list.recycler.adapter.JokeAdapter
import com.example.myjokes.ui.joke_list.recycler.adapter.JokeListAdapter
import com.example.myjokes.ui.joke_list.recycler.util.JokeItemCallback

class JokesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokesListBinding
    private lateinit var viewModel: JokeListViewModel
    val generator = JokeGenerator

    private val adapter = JokeAdapter {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }

//    private val adapter2 = JokeListAdapter (
//        JokeItemCallback(),
//        startActivity(JokeDetailsActivity.getInstance(this, it))
//    ) - я если честно не понял почему мне пишет ошибку, что startActivity возвращает Unit, вместо (Int) -> Unit, а с JokeAdapter такого нет

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        generator.generateJokeData()
        savedInstanceState?.let { setupData(generator.data) } // признаться честно: не понял зачем, и без этого при повороте экрана всё работает, экран повертел, всё то же
        setupData(generator.data)

        initViewModel()
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

    private fun setupData(newData: List<Joke>) {
        adapter.setNewData(newData)
    }
}