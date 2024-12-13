package com.example.myjokes.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myjokes.R
import com.example.myjokes.databinding.BaseFragmentBinding
import java.util.Random
import by.kirich1409.viewbindingdelegate.viewBinding

private const val ARG_PARAM1 = "param1"

class BaseFragment: Fragment(R.layout.base_fragment) {

    private var param1: Int? = null
    private val rnd = Random()

    private val binding: BaseFragmentBinding by viewBinding(BaseFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.text = "Screen ${param1.toString()}"
        binding.nextFragmentButton.setOnClickListener{
            launchNext()
        }
    }

    private fun launchNext() {
        val fragment = newInstance(parentFragmentManager.backStackEntryCount + 1)

        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    companion object {
        fun newInstance(param1: Int) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}