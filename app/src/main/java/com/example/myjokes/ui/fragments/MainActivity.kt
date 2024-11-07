package com.example.myjokes.ui.fragments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myjokes.R
import com.example.myjokes.databinding.ActivityWithFragmentsBinding
import com.example.myjokes.ui.joke_list.JokeListViewModel

class MainActivity: AppCompatActivity() {
    private val viewModel: JokeListViewModel by viewModels()
    private lateinit var binding: ActivityWithFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openJokeListFragment()
            viewModel.generateJokes()
        }
    }

    private fun openJokeListFragment() {
        val fragment = JokeListFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()
    }

    fun openJokeDetailFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, JokeDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

}