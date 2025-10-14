package com.example.todolist_android.data

import androidx.appcompat.widget.DialogTitle

data class Task(
    val id: Int,
    val title: String,
    val done: Boolean
){
    companion object {
        const val TABLE_NAME = "Task"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DONE = "done"

        val SQL_CREATE_TABLE =
            "CREATE TABLE ${TABLE_NAME} (" +
                    "${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COLUMN_TITLE} TEXT," +
                    "${COLUMN_DONE} BOOLEAN)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }
}
