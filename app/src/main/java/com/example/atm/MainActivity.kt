package com.example.atm

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity.apply
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.atm.MyWorker.Companion.TAG
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.builder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.DoubleStream.builder


class MainActivity : AppCompatActivity() {
    //取得儲存目錄
    var addss = Environment.getExternalStorageDirectory()

    //共用環境參數
    companion object {
        val RC_LOGIN = 30
        val REQUEST_CAMERA = 50
        val PICTURE_INTENT = 0

    }

    //28-12 廣播接收器動態註冊
    val myreceiver1 =MyReceiver()
    override fun onStart() {
        super.onStart()
        IntentFilter().apply{
            addAction("com.example.atm.Myreceiver")
        }.also{
            registerReceiver(myreceiver1,it)
        }
    }



    private val REQUEST_EXTERNAL_STORAGE: Int = 0
    var login =false
    //目前轉到MaterialActivity 先顯示畫面
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//一、取得相機、麥克風、聯絡人、簡訊、感測器、位置、日曆權限
        //1.測試可直接執行中要求權限(要現在AndroidManifest.xml 中添加可以要求的權限項目後，再到這邊執行要求權限)
        READ_CONTACTS   //讀取聯絡人紀錄
        //2.檢查權限語法
        val permission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS)
        //3.設計判斷擁有與無權限的程式碼  PERMISSION_DENIED(已獲得權限)  、 PERMISSION_DENIED(無該權限)
        if(permission != PackageManager.PERMISSION_DENIED){
            //未取得權限，向使用者要求允許權限
        }else{
            //已有權限，可進行檔案存取
        }
        //4.請求權限語法 ActivityCompat.requestPermissions( 1.Context物件, 2.參數字串陣列(欲耀球的權限) , 3. int 本次請求的識別碼(回傳時確認是哪次請求用的))
        ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE),REQUEST_EXTERNAL_STORAGE)
        //5.當使用者允許或拒絕權限 : 覆寫onRequestPermissionResult


        getSharedPreferences("atm", MODE_PRIVATE)
        if(!login){
            Intent(this,LoginActivity::class.java).apply {startActivityForResult(this, RC_LOGIN)}
        }





    }



//一、取得相機、麥克風、聯絡人、簡訊、感測器、位置、日曆權限
        //5.當使用者允許或拒絕權限 : 覆寫onRequestPermissionResult
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            //處理傳回來的訊息
            when(requestCode){
                REQUEST_EXTERNAL_STORAGE -> {

                }
            }
        }


//二、加入自訂義 Menu Bar
        //1.覆寫onCreateOptionMenu將menu物件加入 MainActivity
            override fun onCreateOptionsMenu(menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.menu_main,menu)
                return true
            }
        //2.單按下選單中任一項目時會自動呼叫Activity的onOptionsItemSelected方法，並傳來備案下的項目MenuItem物件
            override fun onOptionsItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.action_contacts ->{
                        startActivity(Intent(this,MaterialActivity::class.java))
                    }
                    R.id.action_help ->{

                    }
                    R.id.action_camera ->{
                        val camera = Intent(this,CameraActivity::class.java)
                        startActivityForResult(camera,REQUEST_CAMERA)  //REQUEST_CAMERA 在最上方companion object內有宣告
                    }
                    R.id.action_expense ->{
                        val exp = Intent(this,ExpenseActivity::class.java)
                        startActivity(exp)
                    }
                    R.id.action_transactions->{
                        startActivity(Intent(this,TransActivity::class.java))
                    }
                    R.id.action_service->{
                        startService(Intent(this,MyService::class.java))
                    }
                    R.id.action_chat->{
                        startActivity(Intent(this,ChatActivity::class.java))
                    }
                    R.id.action_work->{
                        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                            .setInitialDelay(30,TimeUnit.SECONDS)
                            .build()
                        WorkManager.getInstance(this)
                            .enqueue(workRequest)
                        val sdf = SimpleDateFormat("HH:mm:ss")
                        Log.d(TAG,"start:${sdf.format(Date())}")
                    }
                }
                return super.onOptionsItemSelected(item)
            }

    //讀取點擊螢幕位置
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        var x  = event?.x  //取x軸
        var y =  event?.y ;//取y軸
        var action = event?.action;//取整數
        when(action){
            MotionEvent.ACTION_DOWN -> Log.d("MotionEvent","按下:"+ x + "," + y)
            MotionEvent.ACTION_MOVE -> Log.d("MotionEvent","平移:"+ x + "," + y)
            MotionEvent.ACTION_UP   -> Log.d("MotionEvent","彈起:"+ x + "," + y)
        }

        return true
    }

    //四、按下此按鈕會開啟MaterialActivity
    fun material_onClick(view:View){
        Intent(this,MaterialActivity::class.java).apply{startActivity(this)}
    }

//五、撥打電話的按鈕
val number = Uri.parse("tel:22334455")
val intent_tel = Intent(Intent.ACTION_DIAL,number)
fun fragment_detail_Button_OnClick(view: View){
    startActivity(intent_tel)
}
    //寄Email (需另外安裝Email軟體才能開啟)
//    val email = Uri.parse("mailto: tom@ibm.com")
//    val intent_email = Intent(Intent.ACTION_SENDTO,email)
//
//    fun email_test_OnClick(view: View){
//        intent_email.putExtra(Intent.EXTRA_SUBJECT,"這是標題")
//        intent_email.putExtra(Intent.EXTRA_TEXT, "這是內容")
//        startActivity(intent_email)
//    }

    //六、打開圖片
    val intent_picture = Intent(Intent.ACTION_GET_CONTENT)
    fun picture_Onclick(view: View){
        intent_picture.setType("image/*")
        //可以使用startActivityForResult來獲得回傳結果
        startActivityForResult(intent_picture, PICTURE_INTENT)

    }

    //進入SwapActivity的按鈕  : 在Fragment如果直接用layout內的Onclick函數，會跑到目前遊覽介面的ActivityClass內找方法，所以還要去那邊新增這個方法
    fun swap_onclick(view: View){

        //一、要先拿到Activity，因為此類別不是繼承AppCompatActivity類別，使用getactivity
//        val MyContext = this
//        //進入SwapActivity的按鈕參數
//        val intent_swapActivity = Intent(MyContext,SwapActivity::class.java).apply { startActivity(this) }

        //二、(原本swap_onclick)使用intent-Filter去開啟Activity
//                val intent_Main = Intent()
//          //執行在AndroidManifest內有插入<intent-filter> <Action>內一樣名子的Activity
//                intent_Main.setAction("com.example.atm.ACTION_A")
//                if(intent_Main.resolveActivity(packageManager) != null){
//                    Log.d("DEBUG","我有執行")
//                    startActivity(intent_Main)
//                }else{
//                    Log.d("DEBUG","null")
//                }
        //二-1、BroadcastReceiver使用intent-Filter去開啟Activity 28-6頁
                val intent_BroadCast = Intent()
//                //使用在一開始30行那邊動態註冊的廣播接收器
                intent_BroadCast.setAction("com.example.atm.Myreceiver")
                sendBroadcast(intent_BroadCast)
        //三、測試BroadcastReceiver，(這個方法在Android 8.0開始，因為系統效能與電池消耗的原因，已不建議使用，請盡可能使用靜態註冊方式)
           //28-12頁
//            val intent_MyReceiver = Intent()
//                .apply { setAction("com.example.atm.CHAT_INCOMING") }
//                .also { sendBroadcast(it) }


    }
//七、Service_Button  //暫時不用
     fun Action_Service_onClick(view: View){
         startService(Intent(this,MyService::class.java))
     }

//八、Intent.Action測試
    var audio_check = false
    fun action_test_Onclick(view: View){
        //使用內建Intent.Action的測試
//        val uri = Uri.parse("file:content://com.android.externalstorage.documents/document/primary%3AMusic%2F%E5%9C%A8%E9%80%99%E5%BA%A7%E5%9F%8E%E5%B8%82%E5%A4%B1%E5%8E%BB%E4%BD%A0%20(1).m4a")
//        val intent_test = Intent(Intent.ACTION_VIEW)
//        intent_test.setDataAndType(uri,"audio/mp3")
//        startActivity(intent_test)
        if(audio_check==false) {
            Toast.makeText(this, "正在播放音樂...", Toast.LENGTH_SHORT).show()
            val intent_audio=Intent(this,MyAudioService::class.java)
            startService(intent_audio)
            audio_check=true
        }else{
            val intent_audio=Intent(this,MyAudioService::class.java)
            stopService(intent_audio)
            audio_check=false
        }
    }



//使用登入時用startActivityForResult 轉到此畫面時需確認傳過來的值  15-11頁
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            //如果回傳值requestCode = RC_LOGIN 則執行下一步

            if(requestCode == RC_LOGIN){
                //如果回傳值resultCode = Activity.RESUL_OK則執行下一步
                if(resultCode == Activity.RESULT_OK){
                    val userid = data?.getStringExtra("LOGIN_USERID")
                    val passwd = data?.getStringExtra("LOGIN_PASSWD")
                    Log.d("RESULT","$userid / $passwd")
//                    Log.d("Login",login.toString())
                }else{
                    //有人直接按上一頁後要執行的方法
                    Log.d("登入失敗","登入失敗啦!!!")
                    if(!login){
                        Intent(this,LoginActivity::class.java).apply {startActivityForResult(this, RC_LOGIN)}
                    }
                }
            //六、 打開圖片後傳入圖片
            //如果回傳碼是Picture_Intent
            }else if(requestCode == PICTURE_INTENT){
                if(resultCode == Activity.RESULT_OK) {
                    picture_intent.setImageURI(data?.data)
                }
            }
        }

    //BroadcastReceiver在此Activity結束時註銷廣播
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myreceiver1)
    }

}