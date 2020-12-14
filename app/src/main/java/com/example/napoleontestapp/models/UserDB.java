package com.example.napoleontestapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "UserDB")

public class UserDB extends Model {
    @Column(name = "userId")
    public int userId;
    @Column(name = "userName")
    public String userName;
    @Column(name = "userEmail")
    public String userEmail;
    @Column(name = "userPhone")
    public String userPhone;

    public void saveUser(int userId, String userName, String userEmail, String userPhone){
        UserDB user = new Select().from(UserDB.class).where("userId = ?", userId).executeSingle();
        if (user==null){
            user = new UserDB();
        }
        user.userId=userId;
        user.userName=userName;
        user.userEmail=userEmail;
        user.userPhone = userPhone;
        user.save();
    }
    public List<UserDB> getUserList(){
        return new Select().from(UserDB.class).execute();
    }

    public List<UserDB> getFilteredUserList(String filter) {
        String orderBy = "userName ASC";
        return new Select().from(UserDB.class)
                .where("userName LIKE '%"+filter+"%' " )
                .orderBy(orderBy)
                .execute();
    }

    public UserDB findUserById(int userId) {
        return new Select().from(UserDB.class).where("userId = ?", userId).executeSingle();
    }
}
