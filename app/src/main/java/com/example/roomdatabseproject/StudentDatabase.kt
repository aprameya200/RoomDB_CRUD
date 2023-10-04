package com.example.roomdatabseproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDatabase: RoomDatabase() { //class to build the database

    abstract fun studentDao(): StudentDao

    companion object{

        @Volatile
        private var INSTANCE : StudentDatabase? = null //hold the refenrence to the database

        /**
         * Gets the instance of the database*
         * @param context
         * @return
         */
        fun getDatabase(context: Context): StudentDatabase{
            val tempInstance = INSTANCE

            //if instancce already exists then that instance is returned
            if (tempInstance != null){
                return tempInstance
            }

            /**
             * else new instance is created using databaseBuilder() method
             *
             *
             * synchronized is used to prevent this code from being executed by multiple threads
             *
             * "this" = the curernt innstance of the class
             */
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,StudentDatabase::class.java,"student_database"
                ).build()

                //new database is assigned to instance
                    INSTANCE = instance
                return  instance
            }
        }



    }
}