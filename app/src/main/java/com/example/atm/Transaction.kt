package com.example.atm


data class Transaction (
    var account:String,
    var data:String,
    var amount:Int,
    var type :Int
    ){
    constructor() : this("","",0,0)

    override fun toString(): String {
        return "$account $data $amount $type"
    }
}