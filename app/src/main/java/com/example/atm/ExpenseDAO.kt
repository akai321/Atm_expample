package com.example.atm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.selects.select
import java.lang.Exception

@Dao
interface ExpenseDAO {
    @Insert
    fun add(exception: Expense)

    @Query("select * from Expense")
        fun getAll() : List<Expense>

    @Query("SELECT * FROM Expense WHERE id LIKE :name")
    fun findByName(name: String): Expense

    @Delete
    fun delete(exception: Expense )

//    @Delete
//    fun delete(table: String, whereClause: String, whereArgs: Array<String>): Int
}