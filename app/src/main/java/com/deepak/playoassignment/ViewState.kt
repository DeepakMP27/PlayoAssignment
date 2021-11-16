package com.deepak.playoassignment

sealed class ViewState<out T : Any>
data class NetworkError(
    val message: String?,
    val errorCode: String = "",
    val throwable: Throwable? = null
) : ViewState<Nothing>()

object Loading : ViewState<Nothing>()
data class Success<out T : Any>(val mData: T) : ViewState<T>()