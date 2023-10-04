package com.example.roomdatabseproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global
import android.text.Editable
import android.widget.Toast
import com.example.roomdatabseproject.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var studentDB: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        studentDB = StudentDatabase.getDatabase(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.write.setOnClickListener {
            writeData()
        }

        binding.read.setOnClickListener {
            readData()
        }

        binding.delete.setOnClickListener {
            deleteData()
        }

        binding.update.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val rollNumber = binding.rollNumber.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNumber.isNotEmpty()) {

            GlobalScope.launch(Dispatchers.IO) {

                studentDB.studentDao().update(firstName,lastName,rollNumber.toInt())
            }

            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.rollNumber.text.clear()

            Toast.makeText(this@MainActivity, "Succesfully Written", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this@MainActivity, "Please Enter Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeData() {

        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val rollNumber = binding.rollNumber.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNumber.isNotEmpty()) {

            val student = Student(null, firstName, lastName, rollNumber.toInt())



            GlobalScope.launch(Dispatchers.IO) {

                studentDB.studentDao().insert(student)
            }

            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.rollNumber.text.clear()

            Toast.makeText(this@MainActivity, "Succesfully Written", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this@MainActivity, "Please Enter Data", Toast.LENGTH_SHORT).show()
        }

    }

    private fun toEditable(text: String): Editable {
        return Editable.Factory.getInstance().newEditable(text)
    }

    private suspend fun displayData(student: Student) {

        withContext(Dispatchers.Main) {
            binding.firstName.text = toEditable(student.firstName.toString())
            binding.lastName.text = toEditable(student.lastName.toString())
            binding.rollNumber.text = toEditable(student.rollNumber.toString())
        }
    }

    private fun readData() {
        val rollNumberSearch = binding.search.text.toString()

        if (rollNumberSearch.isNotEmpty()) {
            lateinit var student: Student

            GlobalScope.launch {

                try {
                    student = studentDB.studentDao().findByRoll(rollNumberSearch.toInt())
                    displayData(student)

                } catch (e: kotlin.UninitializedPropertyAccessException) {

                    val mainHandler = Handler(Looper.getMainLooper())
                    mainHandler.post {
                        Toast.makeText(this@MainActivity, "Does not exist", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
    }


    private fun deleteData() {
        val rollNumberSearch = binding.search.text.toString()

        if (rollNumberSearch.isNotEmpty()) {
            lateinit var student: Student

            GlobalScope.launch {

                try {
                    student = studentDB.studentDao().findByRoll(rollNumberSearch.toInt())
                    studentDB.studentDao().delete(student)

                    val mainHandler = Handler(Looper.getMainLooper())
                    mainHandler.post {
                        Toast.makeText(
                            this@MainActivity,
                            "Student Records have been deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }catch (e: kotlin.UninitializedPropertyAccessException) {

                    val mainHandler = Handler(Looper.getMainLooper())
                    mainHandler.post {
                        Toast.makeText(this@MainActivity, "Does not exist", Toast.LENGTH_SHORT)
                            .show()
                    }
                }



            }
        }

    }
}