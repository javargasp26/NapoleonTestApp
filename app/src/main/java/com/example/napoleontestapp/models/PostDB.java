package com.example.napoleontestapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "PostDB")

public class PostDB extends Model {
    @Column(name = "postUserId")
    public int postUserId;
    @Column(name = "postId")
    public int postId;
    @Column(name = "postTitle")
    public String postTitle;
    @Column(name = "postText")
    public String postText;
    @Column(name = "isPostBlue")
    public int isPostBlue;
    @Column(name = "isFavorite")
    public int isFavorite;


    public void savePost(int postUserId, int postId, String postTitle, String postText){
        PostDB post;
        post = new Select().from(PostDB.class).where("postId = ?", postId).executeSingle();
        if (post == null){
            post = new PostDB();
        }

        post.postUserId=postUserId;
        post.postId = postId;
        post.postTitle=postTitle;
        post.postText = postText;
        post.save();
    }

    public void deletePostById(int postId){
        new Delete().from(PostDB.class).where("postId = ?", postId).execute();
    }

    public List<PostDB> getPostList(){
        return new Select().from(PostDB.class).execute();
    }

    public List<PostDB> getPostListByUserId(int userId) {
        return new Select().from(PostDB.class)
                .where("postUserId = ?", userId)
                .execute();
    }
}
