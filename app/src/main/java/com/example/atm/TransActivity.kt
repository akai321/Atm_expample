package com.example.atm

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.parseCookie
import java.io.BufferedReader
import java.net.URL
import org.json.JSONArray


class TransActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans)
        //https://square.github.io/okhttp/
        //OkHttp連線
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url("https://atm201605.appspot.com/h").build()
        CoroutineScope(Dispatchers.IO).launch{
            //一般Http連線
//            val reader = URL("https://atm201605.appspot.com/h")
//                .openConnection()
//                .getInputStream().bufferedReader()
//            val json = reader.use(BufferedReader::readText)
//            Log.d("TAG",json)

            //OkHttp連線
                val response = client.newCall(request).execute()
                response.body?.run {
//                    Log.d("OkHttp", string())
                    val json = string()
                    //使用內鍵JSON提取JSON資料
//                    parseJSON(json)
                    //使用GSON提取JSON資料
//                    parseGSON(json)
                    //使用Jack提取JSON資料
//                    parseJackson(json)//目前有錯誤

                }


        }
//        var P =Pair(1, 100).forEach(::println)
//        Log.d("Piar",P.toString())
    }

    private fun parseJSON(json: String) {
        val trans = mutableListOf<Transaction>()
        val array = JSONArray(json)
        for(i in 0 until array.length()){
            val obj = array.getJSONObject(i)
            val account = obj.getString("account")
            val date = obj.getString("date")
            val amount = obj.getInt("amount")
            val type = obj.getInt("type")
            val t = Transaction(account,date,amount,type)
            Log.d("ParseJSON",t.toString())
            trans.add(t)
        }
    }

    private fun parseGSON(json: String){
        val gson = Gson()
        val trans = gson.fromJson<ArrayList<Transaction>>(json,object:
            TypeToken<ArrayList<Transaction>>(){}.type)
        trans.forEach { t->
            Log.d("GSON",t.toString())

        }

    }
    private fun parseJackson(json:String){
        val mapper = ObjectMapper().registerModule(KotlinModule())
        val trans : ArrayList<Transaction> = mapper.readValue(json)
        trans.forEach{ t ->
            Log.d("Jackson",t.toString())
        }
    }

    fun Pair<Int, Int>.forEach(block: (Int) -> Unit) {
        for (i in first..second) block.invoke(i)
    }


}