package com.example.atm

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atm.databinding.ActivityMaterialBinding
import kotlinx.android.synthetic.main.contact_row.view.*
import kotlinx.android.synthetic.main.content_material.recycler

class MaterialActivity : AppCompatActivity() {
    companion object{
        val REQUEST_CONTACTS = 100
    }
    val contacts = mutableListOf<contact>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_material)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "想要顯示的訊息字串", Snackbar.LENGTH_LONG)
                .setAction("Action"){
                    Log.d("SNACKBAR","按下ACTION")
                }.setActionTextColor(Color.YELLOW)
                .show()
        }
//Content Provider內容供應器示範
        val permission = ActivityCompat.checkSelfPermission(this,READ_CONTACTS)
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this ,
                arrayOf(READ_CONTACTS,WRITE_CONTACTS),
                REQUEST_CONTACTS)
        }else{
            readContacts()
        }

        /*
//將自製的聯絡人表顯示在Activity上，以一列一列的方式呈現。
        //data
        val contacts = listOf<contact>(
            contact("Hank","6661234"),
            contact("Jack","99838882"),
            contact("Jenny","98881234"),
            contact("Eric","77366363"))
        recycler.setHasFixedSize(true)
        recycler.layoutManager =LinearLayoutManager(this)
        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
                val view = layoutInflater.inflate(R.layout.contact_row,parent,false)
                return ContactViewHolder(view)
            }

            override fun getItemCount(): Int {
                return contacts.size
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }

        }
        recycler.adapter = adapter
        */12

    }
//Content Provider內容供應器 : 示範_執行完申請權限後會自動執行onRequestPermissionsResult
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CONTACTS ->{
                if(grantResults.size > 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        readContacts()
                }else{
                    AlertDialog.Builder(this)
                        .setMessage("必須允許聯絡人權限才能顯示資料")
                        .setPositiveButton("OK",null)
                        .show()
                }
            }
        }
    }

//Content Provider內容供應器 : 執行聯絡人的讀取
        /*  query方法規格如下:    21-12頁
           public final Cursor query(
           Uri uri,                 //欲查詢的URI
           String[] projection,     //查詢回傳的表格欄位
           String selection,        //SQL語法的where -> 取出 "符合條件" 的紀錄值
           String[] selectionArgs,  //當where中有參數時，參數放在此
           String sortOrder         //ASC 或DESC
                                    查詢結果排序字串，格式為:
                                    欄位名稱 ASC|DESC
                                    預設為"ASC":資料由小到大排序，"DESC":資料由大到小排序
           )
         */
            @SuppressLint("Range")
                //SuppressLint是用來壓制新版本程式碼在舊版本的報錯，還有其他兩個類似方法
                // 1.@TargetApi(Build.VERSION_CODES.M)
                /*
                此注解表示這段 code 只能在 API23 以上運行，可以排除高版本 API 在低版本 SDK 的報錯，若達不到 TargetApi那由於我們只是忽略警告而已，在運行時還是會出錯。
                */
                // 2.@RequiresApi(api = Build.VERSION_CODES.M)
                /*
                此註解和 TargetApi 的作用是一樣的，只是在字面上更容易讓人理解是「需要23以上」的環境，官方較推薦使用 RequiresApi 來取代 TargetApi
                 */
    private fun readContacts() {
        val cursor =contentResolver.query(
            //ContactsContract.Contacts.CONTENT_URI //原本範例用的URI
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,   //null代表回傳所有欄位
            null,    //null代表表格中的所有筆數
            null, //null代表不需要提供條件直
            null     //null代表預設
        )
        cursor?.run{
            while(cursor.moveToNext()){
                val id  = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(contact(name,phone))

            }
            Log.d("cursor",contacts.toString())
            setupRecyclerView()
        }

    }
//Content Provider內容供應器 : RecyclerView 方法實作
    private fun setupRecyclerView() {
        recycler.setHasFixedSize(true)
        recycler.layoutManager =LinearLayoutManager(this)
        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
                val view = layoutInflater.inflate(R.layout.contact_row,parent,false)
                return ContactViewHolder(view)
            }

            override fun getItemCount(): Int {
                return contacts.size
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }

        }

        recycler.adapter = adapter
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_material)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }


}


class ContactViewHolder(view:View):RecyclerView.ViewHolder(view){
    val name = view.contact_name
    val phone = view.contact_phone

}