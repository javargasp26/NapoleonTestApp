package com.example.napoleontestapp.mainActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.napoleontestapp.R
import com.example.napoleontestapp.adapters.PostAdapter
import com.example.napoleontestapp.models.PostDB
import com.example.napoleontestapp.models.UserDB
import com.example.napoleontestapp.util.SwipeToDeleteCallback


class PostActivity : AppCompatActivity() {
    //global variables
    var userId = 0
    var context: Context? = null
    var user: UserDB? = null
    var name: TextView? = null
    var recyclerViewPostsResults: RecyclerView? = null
    var postDB: PostDB? = null
    var postAdapter: PostAdapter? = null
    private lateinit var postList: MutableList<PostDB>
    private lateinit var favoritePostList: MutableList<PostDB>
    private lateinit var basedPostList: MutableList<PostDB>

    var userDB: UserDB? = null

    private lateinit var btnShowFavorite: Button
    private lateinit var btnShowAll: Button

    private lateinit var btnDeleteAll: Button
    private lateinit var btnRefresh: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        context = this
        postDB = PostDB()
        userDB = UserDB()

        user = userDB!!.findUserById(userId)
        postList = postDB!!.postList

        favoritePostList = mutableListOf<PostDB>()
        basedPostList = mutableListOf<PostDB>()

        //initialize visual components
        initComponents()
        //build recycler view adapter
        buildPostAdapter()
        //init button listeners
        initListeners()
    }

    private fun initListeners() {
        btnShowFavorite.setOnClickListener{
            showFavorite()
        }

        btnShowAll.setOnClickListener{
            showAll()
        }

        btnDeleteAll.setOnClickListener{
            deleteAll()
        }

        btnRefresh.setOnClickListener{
            refresh()
        }
    }

    private fun showAll() {
        if (favoritePostList.isNotEmpty()){
            postList.clear()
            postList.addAll(basedPostList)
            postAdapter!!.notifyDataSetChanged()
        }
    }

    private fun showFavorite() {
        
        favoritePostList.clear()
        favoritePostList.addAll(postList.filter { it.isFavorite == 1})
        if (favoritePostList.isNotEmpty()){
            basedPostList = postList
            postList.clear()
            postList.addAll(favoritePostList)
            postAdapter!!.notifyDataSetChanged()
        }else{
            Toast.makeText(this, "No hay favoritos seleccionados", Toast.LENGTH_SHORT).show()
        }

    }

    private fun refresh() {
        postList.clear()
        postList.addAll( postDB!!.postList)
        postAdapter!!.notifyDataSetChanged()
    }

    private fun deleteAll() {
        postList.clear()
        postAdapter!!.notifyDataSetChanged()
    }

    private fun initComponents() {

        btnShowFavorite = findViewById(R.id.btnShowFavorite)
        btnShowAll = findViewById(R.id.btnShowAll)

        btnDeleteAll = findViewById(R.id.btnDeleteAll)
        btnRefresh = findViewById(R.id.btnRefresh)

        recyclerViewPostsResults = findViewById(R.id.recyclerViewPostsResults)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewPostsResults!!.layoutManager = linearLayoutManager
    }

    private fun buildPostAdapter() {
        for (i in 0..19) {
            postList[i].isPostBlue = 1
        }
        postAdapter = PostAdapter(context, postList)
        recyclerViewPostsResults!!.adapter = postAdapter

        postAdapter!!.setOnItemClickListener( object: PostAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
            }

            override fun onInformationClicked(position: Int, v: View?) {
                postDB = postList[position]
                goInformation(postDB!!)
            }

            override fun onFavoriteClicked(position: Int, v: View?) {
                postDB = postList[position]
                goChangeFavoriteStatus(postDB!!, 0)
            }

            override fun onNonFavoriteClicked(position: Int, v: View?) {
                postDB = postList[position]
                goChangeFavoriteStatus(postDB!!, 1)
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        })

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerViewPostsResults!!.adapter as PostAdapter
                val position = viewHolder.adapterPosition
                postDB = postList[position]
                postList.removeAt(position)
                removePost(postDB!!)
                //adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerViewPostsResults)

    }

    private fun goChangeFavoriteStatus(postDB: PostDB, isFavorite: Int) {
        postList.find { it.postId == postDB.postId }?.isFavorite = isFavorite
        postAdapter!!.notifyDataSetChanged()
    }

    private fun removePost(postDB: PostDB) {
        var post = PostDB()
        post = postList.find { it.postId == postDB.postId }!!
        postList.remove(post)
        postAdapter!!.notifyDataSetChanged()
    }

    private fun goInformation(postDB: PostDB) {
        val intent = Intent(context, InformationActivity::class.java)
        intent.putExtra("userId", postDB.postUserId)
        context!!.startActivity(intent)
    }
}