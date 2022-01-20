package com.example.ny_task.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.ny_task.R
import com.example.ny_task.adapter.NewsAdapter
import com.example.ny_task.adapter.OnClickListener
import com.example.ny_task.databinding.NewsFragmentBinding
import com.example.ny_task.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@AndroidEntryPoint
class NewsFragment : Fragment() {

    @InternalCoroutinesApi
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsFragmentBinding: NewsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        newsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.news_fragment,container,false)
        newsFragmentBinding.lifecycleOwner = this

        setHasOptionsMenu(true)


        observeItems(setUpAdapter())
        newsViewModel.loader.observe(this as LifecycleOwner) { Loader ->
            when(Loader){
                true-> newsFragmentBinding.progressBar.visibility = View.VISIBLE
                else-> newsFragmentBinding.progressBar.visibility = View.GONE
            }
        }
        setUpNavigation()
        return newsFragmentBinding.root
    }

    private fun setUpAdapter(): NewsAdapter {
        val newsAdapter = NewsAdapter(OnClickListener {
            newsViewModel.displayNewsDetails(it)
        })
        newsFragmentBinding.newsRecyclerView.adapter = newsAdapter
        return newsAdapter
    }

    @InternalCoroutinesApi
    private fun observeItems(newsAdapter: NewsAdapter) {
        newsViewModel.newsList.observe(viewLifecycleOwner,{response->
                response.let {
                    newsAdapter.submitList(it?.getOrNull()?.results)
                }
        })
    }

    private fun setUpNavigation() {
        newsViewModel.navigateToSelectedNews.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(it)
                )
                newsViewModel.displayNewsDetailsComplete()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }

}