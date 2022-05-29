package com.example.atm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    //撥打電話的按鈕
    val number = Uri.parse("tel:22334455")
    val intent_tel = Intent(Intent.ACTION_DIAL,number)
    fun fragment_detail_Button_OnClick(view: View){
        startActivity(intent_tel)
    }

    //進入SwapActivity的按鈕  : 在Fragment如果直接用layout內的Onclick函數，會跑到當前顯示介面的ActivityClass內找方法，
    //                          所以還要去那邊新增這個方法，目前也就是MainActivity
//    fun swap_onclick(view: View){
//        //要先拿到Activity，因為此類別不是繼承AppCompatActivity類別，使用getactivity
//        val MyContext = this.context
//        //進入SwapActivity的按鈕參數
//        val intent_swapActivity = Intent(MyContext,SwapActivity::class.java).apply { startActivity(this) }
//    }



    companion object {
        //未來，只要呼叫類別DetailFragment.instance即可取得唯一的物件
        val instance:DetailFragment by lazy {
            DetailFragment()
        }
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}