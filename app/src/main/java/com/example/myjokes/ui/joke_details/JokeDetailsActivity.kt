package com.example.myjokes.ui.joke_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myjokes.domain.entity.Joke
import com.example.myjokes.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity(), JokeDetailsView {

    private lateinit var binding: ActivityJokeDetailsBinding
    private lateinit var presenter: JokeDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = JokeDetailsPresenter(this)
        handleExtra()
        binding.backButton.setOnClickListener {
            finish() //switch to OnBackPressedDispatcher later
        }
    }

    private fun handleExtra() {
        val jokePosition: Int = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)
        presenter.loadJokeDetails(jokePosition)
    }

    override fun showJokeInfo(joke: Joke) {
        with(binding) {
            jokeCategory.text = joke.category
            jokeQuestion.text = joke.question
            jokeAnswer.text = joke.answer
        }
    }

    override fun showErrorAndCloseScreen(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {

        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun getInstance(context: Context, jokePosition: Int): Intent {
            return Intent(context, JokeDetailsActivity::class.java).apply {
                putExtra(JOKE_POSITION_EXTRA, jokePosition)
            }
        }
    }
}