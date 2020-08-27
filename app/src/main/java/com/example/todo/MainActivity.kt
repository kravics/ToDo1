package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todo.model.*
import com.example.todo.model.store.Renderer
import com.example.todo.model.store.TodoStore
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.selector
import kotlinx.android.synthetic.main.todo_item.view.*

class MainActivity : AppCompatActivity(), Renderer<TodoModel> {
    private lateinit var store: TodoStore

    override fun render(model: LiveData<TodoModel>) {
      model.observe(this, Observer { newstate ->
          listView.adapter = TodoAdapter(this, newstate?.todos ?: listOf())
      })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store = ViewModelProviders.of(this).get(TodoStore::class.java)
        store.subscribe(this, mapStateToProps)

        addButton.setOnClickListener {
            store.dispatch(AddTodo(editText.text.toString()))
            editText.text = null
        }

        fab.setOnClickListener { openDialog() }

        listView.adapter = TodoAdapter(this, listOf())
        listView.setOnClickListener({ adapterView, view, i, l, id ->
            store.dispatch(ToggleTodo(id))
        })

        listView.setOnItemLongClickListener { _, _, _, id ->
            store.dispatch(RemoveTodo(id))
            true
        }
    }

    private fun openDialog (){
        val options = resources.getStringArray(R.array.filter_options).asList()
        selector(getString(R.string.filter_title), options, { _, i ->
            val visible = when(i) {
            1 -> Visibility.Active
            2 -> Visibility.Completed
            else -> Visibility.All
            }
            store.dispatch(SetVisibility(visible))
        })
    }

    private val mapStateToProps = Function<TodoModel, TodoModel>{
        val keep: (Todo) -> Boolean = when(i t.visibility){
            is Visibility.All -> {_ -> true}
            is Visibility.Active -> {t: Todo -> !t.status}
            is Visibility.Completed -> {t:Todo -> t.status}
        }

        return@Function it.copy(todos = it.todos.filter {keep(it)})
    }
}