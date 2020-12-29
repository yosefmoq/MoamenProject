package com.yosefmoq.moamenproject.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yosefmoq.moamenproject.models.User;


public class LocalSave {
    private static final String KEY_USER = "user";
    private static final String IS_LOGIN      = "is_login";

    private static LocalSave instance = null;
    private Context mContext;

    private LocalSave(Context mContext) {
        this.mContext = mContext;
    }

    public static LocalSave getInstance(Context context) {
        if (instance == null) {
            instance = new LocalSave(context);
        }
        return instance;
    }

    private Context getmContext() {
        return mContext;
    }

/*
    public void seveUserInformation(EditProfile editProfile){
        SharedPrefs.save(getmContext(),KEY_USER_INFO,new Gson().toJson(editProfile));
    }
    public EditProfile editProfile(){
        return new Gson().fromJson(SharedPrefs.getString(getmContext(),KEY_USER_INFO),EditProfile.class);
    }*/
    public void  setUserInfo(User userInfo){
        SharedPrefs.save(getmContext(),KEY_USER,new Gson().toJson(userInfo));
    }
    public User getUserInfo(){
        return new Gson().fromJson(SharedPrefs.getString(getmContext(),KEY_USER),User.class);
    }
    public void clear() {
        SharedPrefs.removeKey(getmContext(), KEY_USER);
        SharedPrefs.removeKey(getmContext(),IS_LOGIN);

    }

    public void setLoginAsGuest(boolean isLogin) {
        SharedPrefs.save(getmContext(),IS_LOGIN,isLogin);
    }
    public boolean isLoginGuest(){
        return SharedPrefs.getBoolean(getmContext(),IS_LOGIN);
    }
}
