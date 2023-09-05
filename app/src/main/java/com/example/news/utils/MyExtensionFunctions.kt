package com.example.news.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}


fun Int.toDate(): String? {
    try {
        val sdf = SimpleDateFormat("EE")
        val netDate = Date(this.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}