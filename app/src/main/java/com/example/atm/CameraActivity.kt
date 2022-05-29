package com.example.atm

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_camera.*
import okhttp3.internal.notify

class CameraActivity : AppCompatActivity() {
    val PERMISSION_CAMER = 300
    private  var imageUri: Uri? = null
    private val REQUEST_CAPTURE =500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        if(ActivityCompat.checkSelfPermission(this,CAMERA) == PackageManager.PERMISSION_DENIED||ActivityCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),PERMISSION_CAMER)
        }else{
            openCamera()
        }
    }
//開啟相機
    private fun openCamera(){

            //先建立intent()通道
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //修改相片名稱和內容說明
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE,"My Picture")
                put(MediaStore.Images.Media.DESCRIPTION, "Testing")
            }
            //宣告一個contentResolver內容解析器 修改目標為Media.EXTERNAL_CONTENT_URI(相機內容物), values修改那些東西
            imageUri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
            //啟動camera時額外設定 MediaStore.EXTRA_OUTPUT(相機輸出)
            camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            //開啟相機 , REQUEST_CAPTURE 為標示此轉換Activity的代號碼
            startActivityForResult(camera, REQUEST_CAPTURE)
        }

        //當使用者按下允許或否決後，會自動呼叫onRequestPermissionsResult
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode == PERMISSION_CAMER){
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }else{
                    Toast.makeText(this,"Permission denied" , Toast.LENGTH_LONG).show()
                }
            }
        }
        //接收將拍完照後的資料
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == REQUEST_CAPTURE){
                if(resultCode == Activity.RESULT_OK)
                imageView.setImageURI(imageUri)
            }
        }
}