package com.yosefmoq.moamenproject.activities;

public interface SigninActivityPresenterViewInterface {
    void showErrorMessageForUserNamePassword();

    void showErrorMessageForMaxLoginAttempt();

    void showLoginSuccessMessage();
}
