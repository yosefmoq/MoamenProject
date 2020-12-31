package com.yosefmoq.moamenproject;

import com.yosefmoq.moamenproject.Interfaces.SigninInterface;
import com.yosefmoq.moamenproject.Interfaces.SigninPresent;
import com.yosefmoq.moamenproject.utils.SignInPresent;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SigninActivityTest extends TestCase {

    SigninPresent signinPresent;

    @Mock
    SigninInterface signinInterface;

    @Before
    public void begin() {
        signinPresent = new SignInPresent(signinInterface);
    }

    @Test
    public void startHome(){
        String email = "hamza@gmail.com";
        String password = "123456";

        signinPresent.loginInfo(email, password);

        Mockito.verify(signinInterface).startActivity();
    }

    @Test
    public void emailIsEmpty() {
        String email = "";
        String password = "123456";

        signinPresent.loginInfo(email, password);

        Mockito.verify(signinInterface).printErrorMessage("email is empty");
    }


    @Test
    public void passwordIsEmpty() {
        String email = "hamza@gmail.com";
        String password = "";

        signinPresent.loginInfo(email, password);

        Mockito.verify(signinInterface).printErrorMessage("password is empty");

    }

}
