package com.example.napoleontestapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.napoleontestapp.R
import com.example.napoleontestapp.models.PostDB

class PostAdapter(var context: Context?, private var postList: List<PostDB>?) : RecyclerView.Adapter<PostAdapter.PostViewHolder?>(), View.OnClickListener {

    private val listener: View.OnClickListener? = null
    private var mListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        //inflate view
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return PostViewHolder(v,  mListener as OnItemClickListener)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }

    override fun onClick(view: View?) {
        listener?.onClick(view)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onClick(position: Int)
        fun onInformationClicked(position: Int, v: View?)
        fun onFavoriteClicked(position: Int, v: View?)
        fun onNonFavoriteClicked(position: Int, v: View?)
    }

    override fun onBindViewHolder(postViewHolder: PostViewHolder, position: Int) {
        //set visual information
        postViewHolder.title!!.text = postList?.get(position)!!.postTitle
        postViewHolder.body!!.text = postList?.get(position)!!.postText
        if (postList?.get(position)!!.isFavorite == 1){
            postViewHolder.imgVwFavorite!!.visibility = View.VISIBLE
            postViewHolder.imgVwNonFavorite!!.visibility = View.GONE
        }else{
            postViewHolder.imgVwFavorite!!.visibility = View.GONE
            postViewHolder.imgVwNonFavorite!!.visibility = View.VISIBLE
        }
    }

    class PostViewHolder(itemView: View, listener: OnItemClickListener ) : RecyclerView.ViewHolder(itemView) {

        var title: TextView? = null
        var body: TextView? = null
        var lytContentCard: LinearLayout? = null
        var imgVwNonFavorite: ImageView? = null
        var imgVwFavorite: ImageView? = null

        init {
            title = itemView.findViewById(R.id.title)
            body = itemView.findViewById(R.id.body)
            lytContentCard = itemView.findViewById(R.id.lytContentCard)
            imgVwFavorite = itemView.findViewById(R.id.imgVwFavorite)
            imgVwNonFavorite = itemView.findViewById(R.id.imgVwNonFavorite)

            lytContentCard!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onInformationClicked(position, v)
                }
            })

            imgVwFavorite!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onFavoriteClicked(position, v)
                }
            })

            imgVwNonFavorite!!.setOnClickListener(View.OnClickListener { v ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onNonFavoriteClicked(position, v)
                }
            })

        }
    }

}