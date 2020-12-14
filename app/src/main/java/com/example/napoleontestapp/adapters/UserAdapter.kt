package com.example.napoleontestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.napoleontestapp.R
import com.example.napoleontestapp.models.UserDB

class UserAdapter(private var userList : List<UserDB>, context : Context?) : RecyclerView.Adapter<UserAdapter.UserViewHolder?>(), View.OnClickListener{


    private val listener: View.OnClickListener? = null
    private var mListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //inflate view
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        v.setOnClickListener(this)
        return UserViewHolder(v, mListener as OnItemClickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        //set visual information
        userViewHolder.name!!.text = userList[position].userName
        userViewHolder.phone!!.text = userList[position].userPhone
        userViewHolder.email!!.text = userList[position].userEmail
    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onClick(position: Int)
    }

    override fun onClick(view: View?) {
        listener?.onClick(view)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class UserViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var phone: TextView? = null
        var email: TextView? = null
        var btnViewPost: Button? = null

        init {
            name = itemView.findViewById(R.id.name)
            phone = itemView.findViewById(R.id.phone)
            email = itemView.findViewById(R.id.email)
            btnViewPost = itemView.findViewById<Button>(R.id.btn_view_post)

            btnViewPost!!.setOnClickListener(View.OnClickListener {
                if (itemView.hasFocus()) {
                    itemView.clearFocus()
                    //hide keyboard when item has been clicked
                    if (itemView is EditText) {
                        val imm = itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0)
                    }
                }
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    //click function
                    listener.onClick(position)
                }
            })


        }


    }
}

