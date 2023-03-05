package com.example.listtodo_ps25812.Presenter;

import com.example.listtodo_ps25812.Model.User;
import com.example.listtodo_ps25812.Presenter.Listener.LoginResponseListener;

public class LogInPresenter {
    LoginResponseListener mLoginListener;

    public LogInPresenter(LoginResponseListener loginListener) {
        this.mLoginListener = loginListener;

    }

    public void logIn(User user) {
        mLoginListener.onLoginSuccess();

    }
}
