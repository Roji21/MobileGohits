package com.rozi.gohits.ui.brackets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BracketViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bracket Fragment"
    }
    val text: LiveData<String> = _text
}