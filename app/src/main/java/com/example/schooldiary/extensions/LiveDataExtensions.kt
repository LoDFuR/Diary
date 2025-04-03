package com.example.schooldiary.extensions

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T> LiveData<T>.collectAsState(initial: T): State<T> {
    val state = remember { mutableStateOf(initial) }
    val observer = rememberUpdatedState(Observer<T> { value ->
        state.value = value
    })

    DisposableEffect(this) {
        observeForever(observer.value)
        onDispose {
            removeObserver(observer.value)
        }
    }
    return state
}