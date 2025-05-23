package com.example.contectmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO

    //singleton Design Pattern
    //for database connectivity, for network connections
    //reduce of the memory leaks,  the time of the app,connection  time only one object
companion object{
    @Volatile //makes otomoticly visiable
        private var INSTANCE: UserDatabase ?=null
        fun getInstance(context: Context) : UserDatabase{

            synchronized(this){
                var instance = INSTANCE
                if(instance ==null){
                    //creating the database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_db"
                    ).build()
                }
                return instance
            }
        }
}
}
