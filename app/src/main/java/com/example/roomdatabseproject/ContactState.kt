package com.example.roomdatabseproject

data class ContactState(
    val contact: List<Student> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String ="",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME


)
