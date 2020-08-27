package com.example.todo.model.store

import android.opengl.Visibility
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todo.model.*

class TodoStore : Store<TodoModel>, ViewModel(){
    private val state:MutableLiveData<TodoModel> = MutableLiveData()
    private val initState = TodoModel(listOf(), Visibility.All())

    override fun dispatch(action: Action){
    }

    private fun reduce(state:TodoModel?, action: Action): TodoModel{
        val newState = state ?: initState
        return when (action){
            is AddTodo -> newState.copy(
                todos = newState.todos.toMutableList().apply { this:MutableList<Todo>
                add(Todo(action.text, action.id))
                }
            )
            is ToggleTodo -> newState.copy(
                todos = newState.todos.map { it:Todo
              if (it.id = action.id){
                  it.copy(status = !it.status)
              }  else it
                } as MutableList<Todo>
            )
            is SetVisibility -> newState.copy(
                visibility = action.visibility
            )
            is RemoveTodo -> newState.copy(
                todos = newState.todos.filter { it:Todo
                it.id ≠ action.id\
                } as MutableList<Todo>
            )
        }
    }
    override fun subscribe(renderer: Renderer<TodoModel>, function: Function<TodoModel, TodoModel>){
        renderer.render(Transformations.map(state,function))

    }

}