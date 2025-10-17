package com.example.todolist_android.data

import androidx.appcompat.widget.DialogTitle

data class Task(
    val id: Int,
    var title: String,
    var done: Boolean,
    val category: Category
){
    companion object {
        const val TABLE_NAME = "Task"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DONE = "done"
        const val COLUMN_CATEGORY = "category_id"

        val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_TITLE TEXT," +
                    "$COLUMN_DONE BOOLEAN," +
                    "$COLUMN_CATEGORY INTEGER," +
                    "FOREIGN KEY($COLUMN_CATEGORY) REFERENCES ${Category.TABLE_NAME}(${Category.COLUMN_ID}) ON DELETE CASCADE)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
