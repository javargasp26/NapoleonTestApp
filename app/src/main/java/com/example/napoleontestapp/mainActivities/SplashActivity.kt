package com.example.napoleontestapp.mainActivities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.activeandroid.ActiveAndroid
import com.example.napoleontestapp.R
import com.example.napoleontestapp.models.PostDB
import com.example.napoleontestapp.models.UserDB
import com.example.napoleontestapp.placeHolder.PostHolder
import com.example.napoleontestapp.placeHolder.UserHolder
import com.example.napoleontestapp.info.Post
import com.example.napoleontestapp.info.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity : AppCompatActivity() {



    //global variables
    private var handler: Handler? = null
    private var postDB: PostDB? = null
    private var userDB: UserDB? = null
    private var postDBList: List<PostDB>? = null
    private var userDBList: List<UserDB>? = null
    private var context: Context? = null
    private var retrofit: Retrofit? = null
    //api url
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //initialize database
        ActiveAndroid.initialize(this)
        context = this
        postDB = PostDB()
        userDB = UserDB()
        postDBList = postDB!!.postList
        userDBList = userDB!!.userList
        //check if data has been already downloaded. if not then download it, else go main activity
        if (userDBList!!.isEmpty() || postDBList!!.isEmpty()) {
            //retrofit constructor
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            downloadInfo()
            handler = Handler()
            handler!!.postDelayed({
                val intent = Intent(this@SplashActivity, PostActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        } else {
            val intent = Intent(this@SplashActivity, PostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //download information function
    private fun downloadInfo() {
        //download post object
        val postHolder: PostHolder = retrofit!!.create(PostHolder::class.java)
        //download user object
        val userHolder: UserHolder = retrofit!!.create(UserHolder::class.java)
        val postCall: Call<List<Post>> = postHolder.post
        val userCall: Call<List<User>> = userHolder.users
        //download post petition
        postCall.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                //check if there is error response
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Respuesta: " + response.code(),
                        Toast.LENGTH_LONG).show()
                    return
                }
                val postList: List<Post> = response.body()!!
                val postDB = PostDB()
                for (post in postList) {
                    postDB.savePost(post.getUserId(),
                        post.getId(),
                        post.getTitle(),
                        post.getText())
                }
            }
            //handle failure response
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(context, "Respuesta: $t",
                    Toast.LENGTH_LONG).show()
            }
        })
        //download user petition
        userCall.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                //check if there is error response
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Respuesta: " + response.code(),
                        Toast.LENGTH_LONG).show()
                    return
                }
                val userList: List<User> = response.body()!!
                val userDB = UserDB()
                for (user in userList) {
                    userDB.saveUser(user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone())
                }
            }
            //handle failure response
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context, "Respuesta: $t",
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}