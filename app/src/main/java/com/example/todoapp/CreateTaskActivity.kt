package com.example.todoapp


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.todoapp.databinding.ActivityCreateTaskBinding
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.util.UUID

class CreateTaskActivity : AppCompatActivity() {
    private var dateString = ""
    private var spinnerItems : List<SpinnerItem> =  listOf()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonItem = intent.extras?.getString("item")
        val itemPos = intent.extras?.getInt("position")
        var item : ToDoItem? = null
        val gson = GsonBuilder().create()
        val binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        spinnerItems = listOf(
            SpinnerItem(R.drawable.low_importance_checkbox_background, "low"),
            SpinnerItem(R.drawable.mid_importance_checkbox_background, "mid"),
            SpinnerItem(R.drawable.high_importance_checkbox_background, "high")
        )
        binding.importanceSpinner.adapter = SpinnerAdapter(spinnerItems, this)

        binding.toggleDeadline.isChecked = false
        binding.deadlineDate.visibility = View.GONE

        if (jsonItem != "None") {
            item = gson.fromJson(jsonItem, ToDoItem::class.java)
            fillData(item, binding)
        }

        binding.deleteTask.setOnClickListener {
            intent.putExtra("result", "None")
            intent.putExtra("delete", true)
            intent.putExtra("position", itemPos)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.cancelBtn.setOnClickListener {
            intent.putExtra("result", "None")
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.acceptBtn.setOnClickListener {
            val imp = binding.importanceSpinner.selectedItem as SpinnerItem
            val taskText = binding.taskField.text.toString()
            val tm = LocalDate.now().toString()
            val isDone = binding.taskDoneCheckbox.isChecked
            if (jsonItem == "None") {
                item = ToDoItem(
                    id = UUID.randomUUID().toString(),
                    text = taskText,
                    priority = imp.text,
                    deadline = dateString,
                    isCompleted = isDone,
                    createdDate = tm,
                    modifiedDate = tm
                )
                intent.putExtra("newTask", true)
            } else {
                item?.text = taskText
                item?.priority = imp.text
                item?.deadline = dateString
                item?.modifiedDate = tm
                item?.isCompleted = isDone
                intent.putExtra("newTask", false)
                intent.putExtra("position", itemPos)
            }
            intent.putExtra("result", gson.toJson(item))
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.toggleDeadline.setOnClickListener {
            if(binding.toggleDeadline.isChecked) {
                binding.deadlineDate.visibility = View.VISIBLE
            } else {
                binding.deadlineDate.visibility = View.GONE
            }
        }

        binding.deadlineDate.setOnDateChangeListener { _: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            dateString = "$year-${month+1}-$dayOfMonth"
            val txt = "${getString(R.string.deadline_field)}: $dateString"
            binding.deadlineText.text = txt
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillData(item : ToDoItem, binding: ActivityCreateTaskBinding) {
        dateString = item.deadline.toString()
        val arr = resources.getStringArray(R.array.importance_array)
        val deadlineText = if (item.deadline != null) {
            "${binding.deadlineText.text}: $dateString"
        } else {
            "${binding.deadlineText.text}"
        }

        binding.taskField.setText(item.text)
        binding.deadlineText.text = deadlineText
        binding.importanceSpinner.setSelection(arr.indexOf(item.priority))
        binding.taskDoneCheckbox.isChecked = item.isCompleted

    }

}