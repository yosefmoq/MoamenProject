package com.yosefmoq.moamenproject;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.yosefmoq.moamenproject.activities.SigninActivity;
import com.yosefmoq.moamenproject.utils.Validator;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SigninActivityPresenterTest {
    @Mock
    String password, username;
    EditText etUsername, etPassword;
    Button btnLogin;


        btnLogin.setOnClickListener(View ->

    {
        password = etPassword.getText().toString();
        username = etUsername.getText().toString();
        boolean isValidEmail = Validator.isEmailValid(username);
        if (!isValidEmail) {
            etUsername.setError("Please Enter valid email");

        }

        boolean isValidPassword = Validator.isValidPassword(password);
        if (!isValidPassword) {
            etPassword.setError("Please Enter valid password");
        }
        login(username, password);

    });

    @BeforeClass
    @Before
    @Test
    public void checkUserNameEditTextIsDisplayed() {
        boolean isValidEmail = Validator.isEmailValid(username);
        if (!isValidEmail) {
            etUsername.setError("Please Enter valid email");
            return;
        }
    }


}


