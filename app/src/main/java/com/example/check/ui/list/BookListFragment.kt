package com.example.check.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.check.databinding.BooklistFragBinding
import com.example.check.ui.BookViewModel


class BookListFragment: Fragment() {

    private val bookViewModel: BookViewModel by viewModels()

    lateinit var binding: BooklistFragBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = BooklistFragBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = bookViewModel
            }
        return binding.root
    }

}