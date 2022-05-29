package com.example.atm

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val userid = getSharedPreferences("atm",Context.MODE_PRIVATE).getString("PREF_USERID","")
        ed_userid.setText(userid)
    }
    fun login(view: View){

            //https://atm201605.appspot.com/login?uid=jack&pw=1234
            val userid = ed_userid.text.toString()
            val passwd = ed_passwd.text.toString()
        //透過網路傳訊息過來半段密碼有無錯誤的方法
//            CoroutineScope(Dispatchers.IO).launch {
//                val result = URL("https://atm201605.appspot.com/login?uid=$userid&pw=$passwd").readText()
//                //有兩種讀取網頁回傳數據的方式
//                //1.
//    //            val test = URL("http://atm.snpy.org/get?id=123&func=abc").readText()
//    //            print(test)
//                //2.
//    //            val test = InputStreamReader(URL("http://atm.snpy.org/get?id=123&func=abc").openConnection().getInputStream(),"UTF-8")
//    //            var text1 = test.read()
//    //            while (text1 != -1) {
//    //                print(text1.toChar())
//    //                text1=test.read()
//    //            }
//                Log.d("TAG", result)
//
//                if(result=="1"){
//                    //儲存帳號
//                    getSharedPreferences("atm",Context.MODE_PRIVATE)
//                        .edit()
//                        .putString("PREF_USERID",userid)
//                        .apply()
//                    runOnUiThread {
//                        Toast.makeText(this@LoginActivity, "登入成功", Toast.LENGTH_LONG).show()
//                    }
//                    intent.putExtra("LOGIN_USERID",userid)
//                    intent.putExtra("LOGIN_PASSWD",passwd)
//
//                    setResult(Activity.RESULT_OK,intent)
//                    finish()
//    //                Log.d("登入","成功!!" )
//                }else{
//                    runOnUiThread {
//                        AlertDialog.Builder(this@LoginActivity)
//                            .setTitle("ATM")
//                            .setMessage("登入失敗")
//                            .setPositiveButton("OK", null)
//                            .show()
//                    }
//    //                Log.d("登入","失敗!!" )
//                }
//            }



        //原判斷登入帳號密碼的判斷方法
        if(userid == "jack" && passwd == "1234"){
            getSharedPreferences("atm",Context.MODE_PRIVATE)
                .edit()
                .putString("PREF_USERID",userid)
                .apply()
            Toast.makeText(this,"登入成功",Toast.LENGTH_LONG).show()
            intent.putExtra("LOGIN_USERID",userid)
            intent.putExtra("LOGIN_PASSWD",passwd)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }else{
            AlertDialog.Builder(this)
                .setTitle("ATM")
                .setMessage("登入失敗")
                .setPositiveButton("OK",null)
                .show()
        }
    }

    //派遣觸碰事件(判斷是否為Motion.ACTION_Down)
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var x  = ev?.x  //取x軸
        var y =  ev?.y ;//取y軸
        var action = ev?.action;//取整數

        when(action){
            MotionEvent.ACTION_DOWN -> Log.d("MotionEvent","按下:"+ x + "," + y)
            MotionEvent.ACTION_MOVE -> Log.d("MotionEvent","平移:"+ x + "," + y)
            MotionEvent.ACTION_UP   -> Log.d("MotionEvent","彈起:"+ x + "," + y)
        }

        return super.dispatchTouchEvent(ev)
    }

    //讀取點擊螢幕位置
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//
//        var x  = event?.x  //取x軸
//        var y =  event?.y ;//取y軸
//        var action = event?.action;//取整數
//
//        when(action){
//            MotionEvent.ACTION_DOWN -> Log.d("MotionEvent","按下:"+ x + "," + y)
//            MotionEvent.ACTION_MOVE -> Log.d("MotionEvent","平移:"+ x + "," + y)
//            MotionEvent.ACTION_UP   -> Log.d("MotionEvent","彈起:"+ x + "," + y)
//        }
//
//        return super.onTouchEvent(event)
//    }

    fun cancel(view: View){
        Log.d("取消","取消摟!!")
    }
}