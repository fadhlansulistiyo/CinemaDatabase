package com.fadhlansulistiyo.cinemadatabase.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is people Fragment"
    }
    val text: LiveData<String> = _text
}