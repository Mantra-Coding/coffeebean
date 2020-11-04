package com.example.coffeebean.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.database.entities.User

@Database(entities = [User::class, Product::class], version = 4, exportSchema = false)
abstract class CoffeeDatabase(): RoomDatabase(){

    abstract val coffeeDatabaseDao: CoffeeDatabaseDao

    companion object{

        @Volatile
        private var instance: CoffeeDatabase? = null

        fun getInstance(context:Context): CoffeeDatabase{
            synchronized(this){
                var local = instance
                if (local == null){

                    local = Room.databaseBuilder(
                        context.applicationContext,
                        CoffeeDatabase::class.java,
                        "coffee_db"
                    ).fallbackToDestructiveMigration().build()

                    instance = local
                }

                return local
            }
        }
    }
}