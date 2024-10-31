package com.example.myjokes.ui.joke_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myjokes.R
import com.example.myjokes.data.Joke
import com.example.myjokes.data.JokeGenerator
import com.example.myjokes.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity(), JokeDetailsView {

    private lateinit var binding: ActivityJokeDetailsBinding
    private lateinit var presenter: JokeDetailsPresenter

    private var jokePosition: Int = -1

    companion object {

        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun getInstance(context: Context, jokePosition: Int): Intent {
            return Intent(context, JokeDetailsActivity::class.java).apply {
                putExtra(JOKE_POSITION_EXTRA, jokePosition)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = JokeDetailsPresenter(this)
        handleExtra()
        binding.backButton.setOnClickListener {
            finish() //кстати интересно насколько это правильно
        }
    }

    private fun handleExtra() {
        jokePosition = intent.getIntExtra(JOKE_POSITION_EXTRA, -1)
        presenter.loadJokeDetails(jokePosition)
    }

    override fun showJokeInfo(joke: Joke) {
        with(binding) {
            jokeCategory.text = "${joke.category}"
            jokeQuestion.text = "${joke.question}"
            jokeAnswer.text = "${joke.answer}"
        }
    }

    override fun showErrorAndCloseScreen(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        finish()
    }
}