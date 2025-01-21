package com.example.todoapp

class ToDoItemRepository(private val ls : MutableList<ToDoItem>) {

    fun addItem(item : ToDoItem) : MutableList<ToDoItem> {
        ls.add(item)
        return ls
    }

    fun delItem(id : String) : MutableList<ToDoItem> {
        for (item in ls) {
            if (item.id == id) {
                ls.remove(item)
                break
            }
        }
        return ls
    }

    fun getLs() : MutableList<ToDoItem> = ls

}