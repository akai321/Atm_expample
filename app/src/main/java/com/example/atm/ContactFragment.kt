package com.example.atm

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test","onCreate")
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
        Log.d("test","onCreateView")
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        Log.d("test","onAttach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("test","onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("test","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("test","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("test","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("test","onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("test","onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("test","onDetach")
    }

    companion object {
        //未來，只要呼叫類別ContactFragment.instance即可取得唯一的物件
        val instance:Fragment by lazy{
            ContactFragment()
        }
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}