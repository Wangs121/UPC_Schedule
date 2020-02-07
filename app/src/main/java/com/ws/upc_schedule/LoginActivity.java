package com.ws.upc_schedule;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ws.upc_schedule.Login.LoginFormState;
import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.Login.LoginResult;
import com.ws.upc_schedule.Login.LoginViewModel;
import com.ws.upc_schedule.Login.LoginViewModelFactory;
import com.ws.upc_schedule.Login.Result;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final CheckBox saveLoginCheckBox = findViewById(R.id.remember_password);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
//        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);
        loginViewModel = new ViewModelProvider(this,new LoginViewModelFactory()).get(LoginViewModel.class);

        //检测输入格式是否valid
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {

                    updateUiWithUser();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
//                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
//                finish();
            }
        });
        //登录按钮
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                String[] c = {usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()};
                 new Connect().execute(c);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                Log.d("ws","LoginButton Clicked");
            }
        });
    }
    private void updateUiWithUser() {
        // TODO : initiate successful logged in experience
        LoginRepository.loggedIn(this);
        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();

    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    //登录
    private class Connect extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... ID) {
            String url = "https://app.upc.edu.cn/site/weekschedule/index";
            String url_check = "https://app.upc.edu.cn/uc/wap/login/check";
            String username = ID[0];
            String password = ID[1];
            try {
                Log.d("Cookie", "Start to connect");
                Connection.Response Origin = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .timeout(3000)
                        .execute();
                Log.d("Cookie", Origin.cookies().toString());
                Connection.Response LoginResult = Jsoup.connect(url_check)
                        .data("username", username)
                        .data("password", password)
                        .cookies(Origin.cookies())
                        .ignoreContentType(true)
                        .method(Connection.Method.POST)
                        .timeout(3000)
                        .execute();
                Log.d("Cookie", LoginResult.cookies().toString());
                String body = LoginResult.body();
                Log.d("Cookie", body);
                if (body.contains("操作成功"))  {
                    //保存cookies
                    LoginRepository.WriteCookies(LoginResult.cookies().get("eai-sess"),LoginResult.cookies().get("UUkey"),LoginActivity.this);

                    return "操作成功";
                }
                return body;
            }catch(Exception e) {
                Log.d("Cookie","directly err");
                Log.d("Cookie",e.toString());
                return e.toString();
            }
        }
        @Override
        protected void onPostExecute(String result){
            if(result.equals("操作成功")){
                loginViewModel.setLoginResult(new Result.Success<>("操作成功!"));
            }
            loginViewModel.setLoginResult(new Result.Error(new IOException(result)));
        }
    }
}
