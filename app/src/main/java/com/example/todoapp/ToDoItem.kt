package com.example.todoapp

import java.time.LocalDate

enum class ImportanceField(val st:String) {
    LOW("low"),
    MIDDLE("mid"),
    HIGH("high")
}

data class ToDoItem(
    var id: String,
    var text : String,
    var priority: String,
    var deadline: String?,
    var isCompleted : Boolean,
    var createdDate : String,
    var modifiedDate : String?
)

