package com.example.todo.model.store

import android.opengl.GLSurfaceView
import com.example.todo.model.Action
import java.util.function.Function

interface Store {
    fun distpatch(action: Action)
    fun subscribe(renderer: Renderer<T>, func: Function<T, T> = Function { it }
}