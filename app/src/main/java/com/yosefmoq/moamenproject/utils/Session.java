package com.yosefmoq.moamenproject.utils;

import android.content.Context;


public class Session {
    static Session instance = null;
    static LocalSave localSave = null;

    private Context mContext;


    private Session(Context mContext) {
        this.mContext = mContext;
    }

    public static Session getInstance(Context context) {
        if (instance == null) {
            instance = new Session(context);
            localSave = LocalSave.getInstance(context);

        }
        return instance;
    }

    public LocalSave getLocalSave() {
        return localSave;
    }

    private Context getmContext() {
        return mContext;
    }




/*
    public EditProfile getCurrentUser() {
        return getLocalSave().getCurrentUser();
    }
    public EditProfile getUserInfo(){
        return getLocalSave().editProfile();
    }
*/

    public void clear() {
        localSave.clear();
    }
}
