package com.example.sharedflowdemo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    // История полученных значений
    val messages = mutableStateListOf<Int>()

    init {
        viewModelScope.launch {
            for (i in 1..1000) {
                delay(2000)

                Log.d("SharedFlowDemo", "Emitting $i")

                _sharedFlow.emit(i)
            }
        }
    }
}