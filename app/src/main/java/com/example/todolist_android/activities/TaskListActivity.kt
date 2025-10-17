package com.example.todolist_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist_android.R
import com.example.todolist_android.activities.CategoryActivity.Companion.EXTRA_CATEGORY_ID
import com.example.todolist_android.adapters.HeaderAdapter
import com.example.todolist_android.adapters.TaskAdapter
import com.example.todolist_android.data.Category
import com.example.todolist_android.data.CategoryDAO
import com.example.todolist_android.data.Task
import com.example.todolist_android.data.TaskDAO
import com.example.todolist_android.databinding.ActivityMainBinding
import com.example.todolist_android.databinding.ActivityTaskListBinding
import com.google.android.material.snackbar.Snackbar

class TaskListActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskListBinding

    lateinit var taskDoneAdapter: TaskAdapter
    lateinit var taskToDoAdapter: TaskAdapter
    lateinit var doneHeaderAdapter: HeaderAdapter
    lateinit var toDoHeaderAdapter: HeaderAdapter
    var taskDoneList: List<Task> = emptyList()
    var taskToDoList: List<Task> = emptyList()

    lateinit var category: Category

    lateinit var taskDAO: TaskDAO
    lateinit var categoryDAO: CategoryDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskDAO = TaskDAO(this)
        categoryDAO = CategoryDAO(this)

        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)

        category = categoryDAO.find(categoryId)!!

        supportActionBar?.title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        taskDoneAdapter = TaskAdapter(taskDoneList, { position ->
            // Click
            val task = taskDoneList[position]
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            intent.putExtra("TASK_ID", task.id)
            startActivity(intent)
        }, { position ->
            // Check
            val task = taskDoneList[position]
            task.done = !task.done
            taskDAO.update(task)
            loadData()
        }, { position ->
            // Delete
            val task = taskDoneList[position]

            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(getString(R.string.dialog_message) + "${task.title}?")
                .setPositiveButton(R.string.dialog_positive_button) { dialog, which ->
                    taskDAO.delete(task)
                    loadData()
                    Snackbar.make(binding.root, R.string.dialog_snaker, Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this, "Tarea borrada correctamente", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.dialog_negative_button, null)
                .create()

            dialog.show()

        })

        taskToDoAdapter = TaskAdapter(taskToDoList, { position ->
            // Click
            val task = taskToDoList[position]
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            intent.putExtra("TASK_ID", task.id)
            startActivity(intent)
        }, { position ->
            // Check
            val task = taskToDoList[position]
            task.done = !task.done
            taskDAO.update(task)
            loadData()
        }, { position ->
            // Delete
            val task = taskToDoList[position]

            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title_task)
                .setMessage(getString(R.string.dialog_message_task) + "${task.title}?")
                .setPositiveButton(R.string.dialog_positive_button_task) { dialog, which ->
                    taskDAO.delete(task)
                    loadData()
                    Snackbar.make(binding.root, R.string.dialog_snaker_task, Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this, "Tarea borrada correctamente", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.dialog_negative_button_task, null)
                .create()

            dialog.show()

        })

        toDoHeaderAdapter = HeaderAdapter(getString(R.string.header_adapter_title_toDo))
        doneHeaderAdapter = HeaderAdapter(getString(R.string.header_adapter_title_Done))

        binding.recyclerView.adapter = ConcatAdapter(toDoHeaderAdapter, taskToDoAdapter, doneHeaderAdapter, taskDoneAdapter)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.createButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun loadData() {
        taskDoneList = taskDAO.findAllByCategoryAndDone(category, true)
        taskToDoList = taskDAO.findAllByCategoryAndDone(category, false)
        taskDoneAdapter.updateItems(taskDoneList)
        taskToDoAdapter.updateItems(taskToDoList)
    }
}