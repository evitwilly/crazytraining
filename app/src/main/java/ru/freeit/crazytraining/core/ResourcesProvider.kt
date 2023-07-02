package ru.freeit.crazytraining.core

interface ResourcesProvider {
    fun string(stringResource: Int, vararg args: Any): String
    fun quantityString(stringResource: Int, quantity: Int, vararg args: Any): String
}

