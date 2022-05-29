package com.example.atm

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.android.synthetic.main.expense_alertdialog.view.*
import kotlinx.android.synthetic.main.expense_row.*
import kotlinx.android.synthetic.main.expense_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {
    //lateinit : 代表之後的程式碼會負責初始它(產生它)
    private lateinit var database: ExpenseDatabase
    val expenseData = arrayListOf<Expense>(
        Expense("2021/02/01","Lunch",120),
        Expense("2021/02/02","停車費",60),
        Expense("2021/02/05","日用品",215),
        Expense("2021/02/07","Parking",55)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        //建立database物件(定義何為 1.資料庫實體 2.定義指令方法為何)
        database  = Room.databaseBuilder(this
            ,ExpenseDatabase::class.java,"expense,db").build()

        //將拿取所有資料存成一個快捷地址
        //Query expenses
            //協程(其他執行序)Dispatcher : 可以快速得到預先設計的Main、IO、Default 23-8頁
            //儲存data位置的陣列
            var choices = hashMapOf<Int, Boolean>()


            CoroutineScope(Dispatchers.IO).launch {
                //checkBox確認Hash

                val expenses  = database.expenseDao().getAll()
                Log.d("TAG",expenses.size.toString())
                //創建adapter


                val adapter = object : RecyclerView.Adapter<ExpenseViewHoldwe>() {

                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHoldwe {
                        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_row, parent, false)
                        return ExpenseViewHoldwe(view)
                    }
                    override fun getItemCount(): Int {
                        return expenses.size
                    }
                    override fun onBindViewHolder(holder: ExpenseViewHoldwe, position: Int) {
                        val exp = expenses.get(position)



                        choices.put(position ,holder.itemCheck.isChecked)
                        //確認hashmap是否正確
                        holder.itemCheck.setOnClickListener{
                            choices.set(key = position,holder.itemCheck.isChecked)
                            Log.d("CheckBox","$position 確認布林值${choices.get(position)}")

//                            choices[position] =! choices[position]!!
//                            notifyDataSetChanged()  //通知資料被變動，更新ListView顯示內容
                        }
                        holder.date.setText(exp.date)
                        holder.info.setText(exp.info)
                        holder.amount.setText(exp.amount.toString())




                    }


                }
                runOnUiThread {
                    //刪去database資料
                    fab_Expense_delete.setOnClickListener {
                        val dialog_View = LayoutInflater.from(this@ExpenseActivity).inflate(R.layout.expense_alertdialog, null)
                        val dialog = AlertDialog.Builder(this@ExpenseActivity).setView(dialog_View)
                        dialog.show()
                        dialog_View.alertdialog_button.setOnClickListener {
                            //利用協程去刪除資料
                            CoroutineScope(Dispatchers.IO).launch {
//                                Log.d("迴圈外","我有執行choices.key =${choices.keys}")

                                var a=0
                                var checkHasTrue=0
                                for(key in choices.keys){

//                                    Log.d("迴圈1","我有執行choices.key = ${choices}")
                                    if(choices.get(key)==true){

                                        //呼叫刪除指令
//                                        Log.d("刪除","我有執行第$a 個")
                                        database.expenseDao().delete(expenses.get(a))
                                        checkHasTrue++
                                    }
                                    a++
                                }
                                if(checkHasTrue != 0) {
                                    choices.clear()


                                    //重新啟動APP
//                                    onCreate(null)
                                    //以下兩行完成結束並重啟此Activity
                                        finish()
                                        startActivity(intent)


                                    }
                                runOnUiThread { //以下這些程式完全沒用，但有些有參考性
                                    //要在Alertdialog關閉後再重新整理 不然會回到一開始(舊資料的畫面)進的畫面
                                    //因為觸發關閉的按鈕沒設置所有他永遠不會觸發關閉
//                                    dialog.setOnDismissListener{
//                                        recycle_expense.layoutManager = LinearLayoutManager(this@ExpenseActivity)
//                                        adapter.notifyDataSetChanged()
////                                        recycle_expense.adapter=adapter
////                                        this@ExpenseActivity.onRestart()
//                                        finish()
//                                        startActivity(intent)
//                                    }


                                }
                                //                                    val exp_size = database.expenseDao().getAll().size
                                //                                    for(i in 0..10) {
                                //                                        val exp = database.expenseDao().getAll().get(i)
                                //                                        val find = database.expenseDao().findByName("1")
                                //                                        Log.d("Expense_FindByName", exp.id.toString() + "  我有執行")
                                //                                    }

                            }
                        }
                    }
                    recycle_expense.setHasFixedSize(true)
                    recycle_expense.layoutManager = LinearLayoutManager(this@ExpenseActivity)
                    recycle_expense.adapter=adapter


                }
            }
            //原本會出錯的程式碼
//            val expense : List<Expense> = database.expenseDao().getAll()
//            Log.d("TAG",expense.size.toString())

            //新增資料
            fab_Expense.setOnClickListener{
                Executors.newSingleThreadExecutor().execute{
                    //add
                    for(expense : Expense in expenseData) {
                        database.expenseDao().add(expense)


                    }
                    //以下兩行完成結束並重啟此Activity，要放在for迴圈外不然回到上一頁會會在上一個沒新增的狀態頁上
                    finish()
                    startActivity(intent)

                }
            }




    }
}



class ExpenseViewHoldwe(itemView: View):RecyclerView.ViewHolder(itemView){
    val itemCheck = itemView.checkBox
    val date = itemView.exp_date
    val info = itemView.exp_info
    val amount = itemView.exp_amount
}