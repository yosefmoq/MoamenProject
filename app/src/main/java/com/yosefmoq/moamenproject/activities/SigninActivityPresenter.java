package com.yosefmoq.moamenproject.activities;

/**
 * Created by nileshjarad on 28/07/16.
 */

class SigninActivityPresenter {

    private static final int MAX_LOGIN_ATTEMPT = 3;
    private final SigninActivityPresenterViewInterface loginView;
    private int loginAttempt;

    SigninActivityPresenter(SigninActivityPresenterViewInterface loginView) {
        this.loginView = loginView;
    }

    int incrementLoginAttempt() {
        loginAttempt = loginAttempt + 1;
        return loginAttempt;
    }

    private void resetLoginAttempt() {
        loginAttempt = 0;
    }

    boolean isLoginAttemptExceeded() {
        return loginAttempt >= MAX_LOGIN_ATTEMPT;
    }


    void doLogin(String userName, String password) {
        if (isLoginAttemptExceeded()) {
            loginView.showErrorMessageForMaxLoginAttempt();
            return;
        }

        if (userName.equals("nj") && password.equals("tdd")) {
            loginView.showLoginSuccessMessage();
            resetLoginAttempt();
            return;
        }

        // increment login attempt only if it's fail
        incrementLoginAttempt();
        loginView.showErrorMessageForUserNamePassword();
    }
}
