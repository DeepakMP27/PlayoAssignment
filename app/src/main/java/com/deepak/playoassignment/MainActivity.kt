package com.deepak.playoassignment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deepak.playoassignment.data.model.Article
import com.deepak.playoassignment.data.model.TopHeadlinesResponse
import com.deepak.playoassignment.data.repository.DataRepository
import com.deepak.playoassignment.data.service.RetrofitService
import com.deepak.playoassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var newsAdapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = DataRepository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)

        viewModel.getHeadlines("techcrunch", "8d013e9238b34fcb8fd59e5bd63b039a")
        viewModel.getHeadlineData().observe(this, Observer { onHeadlineData(it) })

    }

    private fun initViews() {
        mLayoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.layoutManager = mLayoutManager
    }

    private fun onHeadlineData(state: ViewState<TopHeadlinesResponse>?) {
        when (state) {
            is Loading -> setProgress(true)
            is NetworkError -> {
                setProgress(false)
                showError("Unable to retrive data. Please try later")
            }
            is Success -> {
                setProgress(false)
                Log.i("data",state.mData.totalResults.toString())
                populateUI(state.mData.articles)
            }
        }
    }

    private fun populateUI(articles: List<Article>?) {
        newsAdapter = NewsAdapter(articles)
        binding.newsRecyclerView.adapter = newsAdapter

    }

    private fun setProgress(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}