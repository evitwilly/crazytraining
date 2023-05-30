package ru.freeit.crazytraining.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun viewModelFactory(func: () -> ViewModel): ViewModelProvider.Factory {
    return object: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return func.invoke() as T
        }
    }
}