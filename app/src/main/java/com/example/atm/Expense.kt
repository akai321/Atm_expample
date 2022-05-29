package com.example.atm
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Expense (
    @NonNull
    @ColumnInfo(name = "dateAt")
    var date:String,
    var info:String,
    var amount: Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0


}