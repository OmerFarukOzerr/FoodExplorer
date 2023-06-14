package com.example.foodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.model.Meals
import com.example.foodapp.model.MealsDetail


@Database(entities = [MealsDetail::class, Meals::class], version = 1)

abstract class DataBase : RoomDatabase(){

    abstract fun getDao () : Dao
}