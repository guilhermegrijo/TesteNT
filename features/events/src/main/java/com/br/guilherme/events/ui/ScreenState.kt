package com.br.guilherme.events.ui

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error(
        val t: Throwable
    ) : ScreenState<Nothing>()



    companion object {
        @JvmStatic
        @BindingAdapter("hideOnLoading")
        fun View.hideOnLoading(responseState: ScreenState<Any>?) {
            visibility = if (responseState is Loading)
                View.GONE
            else
                View.VISIBLE
        }

        @JvmStatic
        @BindingAdapter("swipeHide")
        fun SwipeRefreshLayout.swipeHide(responseState: ScreenState<Any>?) {
            isRefreshing = false
        }

        @JvmStatic
        @BindingAdapter("showOnLoading")
        fun View.showOnLoading(responseState: ScreenState<Any>?) {
            visibility = if (responseState is Loading)
                View.VISIBLE
            else
                View.GONE
        }



        @JvmStatic
        @BindingAdapter("showError")
        fun View.showError(responseState: ScreenState<Any>?) {
            visibility = if (responseState is Error)
                View.VISIBLE
            else
                View.GONE
        }
    }
}

