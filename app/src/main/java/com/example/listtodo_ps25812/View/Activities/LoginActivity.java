package com.example.listtodo_ps25812.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listtodo_ps25812.Model.User;
import com.example.listtodo_ps25812.Presenter.Listener.LoginResponseListener;
import com.example.listtodo_ps25812.Presenter.LogInPresenter;
import com.example.listtodo_ps25812.R;
import com.example.listtodo_ps25812.databinding.ActivityLoginBinding;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements LoginResponseListener {
    ActivityLoginBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityLoginBinding.inflate(getLayoutInflater(), null, false);
        setContentView(layoutBinding.getRoot());
        setEventKeyboard();
        layoutBinding.btnLogin.setOnClickListener(v -> checkLogin());
    }
    void setEventKeyboard() {
        KeyboardVisibilityEvent.setEventListener(
                this,
                isOpen -> {
                    if (isOpen) {
                        hideBanner();
                    } else {
                        showBanner();
                    }
                });
    }

    private void hideBanner() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_hide);
        layoutBinding.anmListCheck.startAnimation(animation);
        layoutBinding.anmListCheck.setVisibility(View.GONE);
    }

    private void showBanner() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_show);
        layoutBinding.anmListCheck.startAnimation(animation);
        layoutBinding.anmListCheck.setVisibility(View.VISIBLE);
    }
    void checkLogin(){
        String userName = Objects.requireNonNull(layoutBinding.edAccount.getText()).toString();
        String password = Objects.requireNonNull(layoutBinding.edPassWord.getText()).toString();
        LogInPresenter  logInPresenter = new LogInPresenter(this);
        logInPresenter.logIn(new User(userName,password));
    }
    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onLoginSuccess() {
        startHomeActivity();
    }

    @Override
    public void onLoginError() {
        layoutBinding.tilPassword.setError("Tên tài khoản hoặc mật khẩu không chính xác !");
    }
}