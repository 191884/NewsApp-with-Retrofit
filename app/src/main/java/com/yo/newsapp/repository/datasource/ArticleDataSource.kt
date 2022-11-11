package com.yo.newsapp.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yo.newsapp.repository.service.RetrofitClient
import com.yo.newsapp.utils.Constants
import com.yo.newsapp.utils.Resource
import com.yo.newsapp.model.Article
import com.yo.newsapp.model.NewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticleDataSource(private val scope: CoroutineScope): PageKeyedDataSource<Int, Article>() {

    //For Breaking News
    val breakingNews: MutableLiveData<MutableList<Article>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse: NewsResponse? = null

    //For Searching News
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse : NewsResponse? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("in", 1, Constants.API_KEY)
                when{
                    response.isSuccessful-> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it,null,2)
                        }
                    }
                }
            }catch (exception: Exception){
                Log.e("DataSource:: ", exception.message.toString())
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch{
                val response = RetrofitClient.api.getBreakingNews(
                    "in",
                    params.requestedLoadSize,
                    Constants.API_KEY
                )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key+1)
                        }
                    }
                }
            }
        }catch (exception: Exception){
            Log.e("DataSource:: ", exception.message.toString())
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }
}