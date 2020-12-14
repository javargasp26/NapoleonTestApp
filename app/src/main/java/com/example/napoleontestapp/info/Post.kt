package com.example.napoleontestapp.info

import com.google.gson.annotations.SerializedName

class Post {
    private val userId = 0
    private val id = 0
    private val title: String? = null

    @SerializedName("body")
    private val text: String? = null
    fun getUserId(): Int {
        return userId
    }

    fun getId(): Int {
        return id
    }

    fun getTitle(): String? {
        return title
    }

    fun getText(): String? {
        return text
    }
}