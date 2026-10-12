
package com.example.sharedflowdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf

class DemoViewModel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    // Список хранится во ViewModel и переживает поворот экрана
    val messages = mutableStateListOf<Int>()

    init {
        sharedFlowInit()
        collectSharedFlow()
    }

    private fun sharedFlowInit() {
        viewModelScope.launch {
            for (i in 1..1000) {
                delay(2000)
                Log.d("SharedFlowDemo", "Emitting $i")
                _sharedFlow.emit(i)
            }
        }
    }

    private fun collectSharedFlow() {
        viewModelScope.launch {
            sharedFlow.collect { value ->
                Log.d("SharedFlowDemo", "Collecting $value")
                messages.add(value)
            }
        }
    }
}
