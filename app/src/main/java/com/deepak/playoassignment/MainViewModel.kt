package com.deepak.playoassignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.playoassignment.data.model.TopHeadlinesResponse
import com.deepak.playoassignment.data.repository.DataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private var mHeadlineData: MutableLiveData<ViewState<TopHeadlinesResponse>> = MutableLiveData()

    private fun onError(throwable: Throwable) {
        mHeadlineData.value = NetworkError(throwable.message)
    }

    fun getHeadlineData(): MutableLiveData<ViewState<TopHeadlinesResponse>> {
        return mHeadlineData
    }


    fun getHeadlines(source: String, apiKey: String) {
        mHeadlineData.value = Loading
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        // viewModelScope launch the new coroutine on Main Dispatcher internally
        viewModelScope.launch(coroutineExceptionHandler) {
            // Create User Registration
            val headlineData = dataRepository.getAllHeadlines(
                source, apiKey
            )

            if (headlineData.status == "ok") {
                // Return the result on main thread via Dispatchers.Main
                mHeadlineData.value = Success(headlineData)
            } else {
                mHeadlineData.value = NetworkError("Unable to retrieve data. Please try later")
            }
        }
    }

}