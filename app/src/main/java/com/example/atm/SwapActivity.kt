package com.example.atm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_swap.*

class SwapActivity : AppCompatActivity() {
    private lateinit var detail:DetailFragment
    private lateinit var contact:ContactFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        contact = ContactFragment()
        detail = DetailFragment()

        supportFragmentManager.beginTransaction().run {
            add(R.id.container,contact)
            commit()
        }

        Fab_Swp.setOnClickListener{
            swap()
        }


    }

    private fun swap(){
        supportFragmentManager.beginTransaction().run {
            //先使用findFragmentById拿取container中的Fragment，之後再進行比較確認
            var temp = supportFragmentManager.findFragmentById(R.id.container)
            if(temp == contact) {
                replace(R.id.container, detail)
            }else if (temp == detail){
                replace(R.id.container,contact)
            }
            //在SwapActivity中按下返回鍵時，回到上一個片段，呼叫addToBackStack(null)，在按下返回時可結束該活動，
            // 將此交易推進記憶堆疊
            addToBackStack(null)
            commit()
        }
    }
}