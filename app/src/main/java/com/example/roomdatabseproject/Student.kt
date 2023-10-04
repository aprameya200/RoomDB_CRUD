package com.example.roomdatabseproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val rollNumber: Int?,


    ) {}