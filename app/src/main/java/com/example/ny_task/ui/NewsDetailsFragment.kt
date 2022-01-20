package com.example.ny_task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ny_task.R
import com.example.ny_task.databinding.NewsDetailsFragmentBinding
import com.example.ny_task.viewmodels.NewsDetailsViewModel
import com.example.ny_task.models.Result as NewsResult

class NewsDetailsFragment : Fragment() {

    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var newsDetailsFragmentBinding : NewsDetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        newsDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.news_details_fragment,container,false)
        newsDetailsFragmentBinding.lifecycleOwner = this
        displayNewsDetails(NewsDetailsFragmentArgs.fromBundle(requireArguments()).selectedNews)

        return newsDetailsFragmentBinding.root
    }


    private fun displayNewsDetails(result: NewsResult) {
        newsDetailsFragmentBinding.titleTextView.text = result.title
        newsDetailsFragmentBinding.detailTextView.text = result.abstract
        newsDetailsFragmentBinding.dateTextView.text = result.published_date
    }

}