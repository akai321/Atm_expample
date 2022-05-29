package com.example.atm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Binder
import android.util.Log


class ChatService : Service() {

       val TAG = ChatService::class.java.simpleName
       val mBinder = ChatBinder()

        inner class ChatBinder : Binder(){
            fun getService() = this@ChatService
        }
        override fun onBind(intent: Intent):IBinder{
            return mBinder
        }

        fun sendmesage(message:String){
            Log.d(TAG,"sendMessage:$message")
        }
        fun deleteMesage(){
            Log.d(TAG,"deleteMessage")
        }


}