package com.example.atm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MyAudioService : Service() {
    val uri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AMusic%2F%E5%9C%A8%E9%80%99%E5%BA%A7%E5%9F%8E%E5%B8%82%E5%A4%B1%E5%8E%BB%E4%BD%A0%20(1).mp3")
    var mediaPlayer:MediaPlayer? = MediaPlayer()




    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    override  fun onCreate() {
//        mediaPlayer = MediaPlayer.create(this,R.raw.lose_you_in_city)//建立音樂服務。播放R.raw中的音樂liyue
            try {
//                mediaPlayer?.setDataSource("content://com.android.externalstorage.documents/document/primary%3AMusic%2F%E5%9C%A8%E9%80%99%E5%BA%A7%E5%9F%8E%E5%B8%82%E5%A4%B1%E5%8E%BB%E4%BD%A0%20(1).mp3")
                mediaPlayer = MediaPlayer.create(this,R.raw.lose_you_in_city)
//                mediaPlayer?.prepareAsync()
                mediaPlayer?.start();//開始服務
            } catch (e: Exception) {
                Log.d("AudioError", e.message.toString())
            }

    }
    override fun onDestroy() {
        Toast.makeText(this, "關閉音樂", Toast.LENGTH_SHORT).show()
        mediaPlayer?.stop()
//        mediaPlayer?.release()
    }



}