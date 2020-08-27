package com.example.todo.model.store

import androidx.lifecycle.LiveData

interface Renderer<T> {
    fun render(model: LiveData<T>)
}