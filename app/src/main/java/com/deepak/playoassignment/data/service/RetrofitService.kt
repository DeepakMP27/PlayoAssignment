package com.deepak.playoassignment.data.service

import com.deepak.playoassignment.data.model.TopHeadlinesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("v2/top-headlines")
    suspend fun getAllMovies(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): TopHeadlinesResponse

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}