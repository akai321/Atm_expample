package com.example.atm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    private val TAG:String = "BootBroadcastReceiver";
    private val ACTION_BOOT:String = "android.intent.action.BOOT_COMPLETED";
    private val TAG2:String = "ShutdBroadcastReceiver";
    private val ACTION_SHUTDOWN:String = "android.intent.action.ACTION_SHUTDOWN";
    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("DEBUG","onReceive")
        Toast.makeText(context, "啟動完成", Toast.LENGTH_LONG).show()
        //開機啟動完成後，要做的事情
        if (intent.getAction().equals(ACTION_BOOT)) {
            Log.d(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
        }
        //即將關機時，要做的事情
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            Log.d(TAG2 , "ShutdownBroadcastReceiver onReceive(), Do thing!");
        }
    }
}