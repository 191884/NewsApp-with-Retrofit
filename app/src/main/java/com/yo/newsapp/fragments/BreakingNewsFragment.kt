package com.yo.newsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.yo.newsapp.MainActivity
import com.yo.newsapp.R
import com.yo.newsapp.adapter.ArticleAdapter
import com.yo.newsapp.databinding.FragmentBreakingNewsBinding
import com.yo.newsapp.utils.shareNews
import com.yo.newsapp.viewModel.NewsViewModel
import kotlin.random.Random

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var binding: FragmentBreakingNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newAdapter: ArticleAdapter
    private lateinit var rvbreakingNews: RecyclerView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private fun setupRecyclerView(){
        rvbreakingNews = rvbreakingNews.findViewById(R.id.rvBreakingNews)
        newAdapter = ArticleAdapter()
        rvbreakingNews.apply{
            adapter = newAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newAdapter.setOnItemClickListener {
            var bundle = Bundle().apply{
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment, bundle)
        }

        newAdapter.onSaveClickListener {
            if (it.id  == null){
                it.id = Random.nextInt(0,1000)
            }
            viewModel.insertArticle(it)
            Snackbar.make(requireView(),"Saved", Snackbar.LENGTH_SHORT).show()
        }

        newAdapter.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Removed", Snackbar.LENGTH_SHORT).show()
        }

        newAdapter.onShareClickListener {
            shareNews(context,it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)
        rvbreakingNews = view.findViewById(R.id.rvBreakingNews)
        viewModel = (activity as MainActivity).viewModel as NewsViewModel
        setupRecyclerView()
        setViewmodelObserver()
    }

    private fun setViewmodelObserver(){
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { newsResponse ->
            when(newsResponse){
                is com.yo.newsapp.utils.Resource.Success -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.data?.let { news ->
                        rvbreakingNews.visibility  = View.VISIBLE
                        newAdapter.differ.submitList(news.articles)
                    }
                }
                is com.yo.newsapp.utils.Resource.Error -> {
                    shimmerFrameLayout.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e("Breaking News Fragment", "Error: $message" )
                    }
                }

                is com.yo.newsapp.utils.Resource.Loading -> {
                    shimmerFrameLayout.startShimmer()
                }
            }
        })
    }

}