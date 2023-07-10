package ru.freeit.crazytraining.core.cache

interface PersistentStringStorage {
    fun string(key: String, default: String): String
    fun save(key: String, value: String)
    fun saveNow(key: String, value: String)
}