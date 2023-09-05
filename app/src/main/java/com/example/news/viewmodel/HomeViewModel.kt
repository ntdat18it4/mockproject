package com.example.news.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    var checkLoadData : Parcelable? = null
}