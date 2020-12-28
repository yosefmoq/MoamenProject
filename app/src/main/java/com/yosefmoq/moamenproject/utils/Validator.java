package com.yosefmoq.moamenproject.utils;

public class Validator {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        public static boolean isEmailValid(String email){
                return email.length() > 10 && email.contains("@") && email.contains(".");
        }

        public static boolean isValidUsername(String name){
                return name.length()>6;
        }
        public static boolean isValidPassword(String password){
                return password.length()>6;
        }
        public static boolean isValidConfirmPassword(String password,String confirmPassword){
                return password.equals(confirmPassword);
        };
}
