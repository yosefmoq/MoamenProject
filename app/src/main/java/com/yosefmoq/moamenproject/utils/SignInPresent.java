package com.yosefmoq.moamenproject.utils;

import com.yosefmoq.moamenproject.Interfaces.SigninInterface;
import com.yosefmoq.moamenproject.Interfaces.SigninPresent;

public class SignInPresent implements SigninPresent {
    SigninInterface signinInterface;

    public SignInPresent(SigninInterface signinInterface) {
        this.signinInterface = signinInterface;
    }

    @Override
    public void loginInfo(String email, String password) {
        if (email.trim().isEmpty()) {
            signinInterface.printErrorMessage("email is empty");
        } else if (password.trim().isEmpty()){
            signinInterface.printErrorMessage("password is empty");
        } else {
            signinInterface.startActivity();
        }
    }
}
