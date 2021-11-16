package com.deepak.playoassignment.data.repository

import com.deepak.playoassignment.data.service.RetrofitService

class DataRepository constructor(private val service: RetrofitService) {

    suspend fun getAllHeadlines(source: String, apiKey: String) =
        service.getAllMovies(source, apiKey)
}