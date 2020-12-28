package com.yosefmoq.moamenproject;

import com.yosefmoq.moamenproject.utils.Validator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthTest {

        @Test
        public void validateEmail(){
                assertEquals(Validator.isEmailValid("yosefmoqq@gmail.com"),true);
        }

        @Test
        public void validateUsername(){
                assertEquals(Validator.isValidUsername("yosefmoq"),true);
        }

        @Test
        public void validatePassword(){
                assertEquals(Validator.isValidPassword("12345678"),true);
        }

        @Test
        public void validateConfirmPassword(){
                assertEquals(Validator.isValidConfirmPassword("12345678","12345678"),true);
        }
}
