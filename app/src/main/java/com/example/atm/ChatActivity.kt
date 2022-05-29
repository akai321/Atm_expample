package com.example.atm

import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log


class ChatActivity : AppCompatActivity(), ServiceConnection {
    var chatService : ChatService? = null
    val TAG = ChatActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val intent = Intent(this,ChatService::class.java)
        bindService(intent,this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        chatService = null
        Log.d(TAG,"onServiceDisconnected")
    }
    override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
        Log.d(TAG,"onServiceConnected")
        val binder = service as ChatService.ChatBinder
        chatService = binder.getService()
    }

    override fun onStop() {
        super.onStop()
        unbindService(this)
    }


}