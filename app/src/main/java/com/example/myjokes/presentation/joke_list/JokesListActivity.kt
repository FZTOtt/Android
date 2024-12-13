package com.example.myjokes.presentation.joke_list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjokes.R
import com.example.myjokes.data_clean.datasource.local.AppDatabase
import com.example.myjokes.data_clean.datasource.local.LocalDataSourceImpl
import com.example.myjokes.data_clean.datasource.remote.ApiService
import com.example.myjokes.data_clean.datasource.remote.RemoteDataSource
import com.example.myjokes.data_clean.datasource.remote.RemoteDataSourceImpl
import com.example.myjokes.data_clean.datasource.remote.RetrofitInstance
import com.example.myjokes.data_clean.mapper.CachedJokeMapper
import com.example.myjokes.data_clean.mapper.JokeDtoMapper
import com.example.myjokes.data_clean.mapper.UserJokeMapper
import com.example.myjokes.data_clean.repository.JokeRepository
import com.example.myjokes.databinding.ActivityJokesListBinding
import com.example.myjokes.presentation.fragments.BaseFragment
import com.example.myjokes.ui.joke_details.JokeDetailsActivity
import com.example.myjokes.presentation.joke_list.recycler.adapter.JokeAdapter

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
//        viewModel.generateJokes()
        if (savedInstanceState == null) {
            onFragment()
        }
    }

    private fun onFragment() {

        val fragment = BaseFragment.newInstance(1)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initViewModel() {
        val remoteDataSource = RemoteDataSourceImpl(RetrofitInstance.api)
        val localDataSource = LocalDataSourceImpl(AppDatabase.INSTANCE.jokeDao())
        val cachedJokeMapper = CachedJokeMapper()
        val userJokeMapper = UserJokeMapper()
        val jokeDtoMapper = JokeDtoMapper()
        val jokeRepository = JokeRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            cachedJokeMapper = cachedJokeMapper,
            userJokeMapper = userJokeMapper,
            jokeDtoMapper = jokeDtoMapper
        )
        val viewModelFactory = JokesViewModelFactory(jokeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[JokeListViewModel::class.java]

        viewModel.jokes.observe(this) { adapter.setNewData(it) }
        viewModel.error.observe(this) { showError(it.toString()) }
    }

    private fun showError(it: String?) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}