package com.ws.upc_schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ws.upc_schedule.Login.LoginDateUtils;
import com.ws.upc_schedule.Login.LoginFormState;
import com.ws.upc_schedule.Login.LoginParser;
import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.Login.LoginResult;
import com.ws.upc_schedule.Login.LoginViewModel;
import com.ws.upc_schedule.Login.LoginViewModelFactory;
import com.ws.upc_schedule.Login.LogindbHelper;
import com.ws.upc_schedule.Login.Result;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {
    private int back_counter = 0;
    private LoginViewModel loginViewModel;
    private ProgressBar loadingProgressBar;
    private LogindbHelper logindbHelper;
    private String term = null;
    private String year;
    private String[] cookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        logindbHelper = new LogindbHelper(getApplicationContext());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
//        term = LoginRepository.getTerm(getApplicationContext());
        year = LoginDateUtils.getYear();
        //检测输入格式是否有效
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

        //检查登录结果
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    loadingProgressBar.setVisibility(View.GONE);
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser();
                }
            }
        });
        //登录按钮
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                String[] c = {usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()};
                new Login().execute(c);
            }
        });
    }

    private void updateUiWithUser() {
        // TODO : initiate successful logged in experience
        LoginRepository.loggedIn(getApplicationContext());
        if (term == null) {
            new get_Term().execute();
        }
        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        new init_Classes().execute();
    }

    private void showLoginFailed(String errorString) {
        if (errorString.contains("java.net.SocketTimeoutException")) {
            Toast.makeText(getApplicationContext(), "连接超时,请检查网络连接或连接校园网后再试", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        }
    }

    //禁用返回键
    @Override
    public void onBackPressed() {
        if (back_counter++ == 0) {
            Toast.makeText(getApplicationContext(), "再次点击返回键退出", Toast.LENGTH_LONG).show();
        } else {
            finishAffinity();
        }
    }

    //登录
    public class Login extends AsyncTask<String, Void, String> {

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
//                Log.d("Cookie", LoginResult.cookies().toString());
                String body = LoginResult.body();
                Log.d("Cookie", body);
                if (body.contains("操作成功")) {
                    //保存cookies
                    cookies = new String[]{LoginResult.cookies().get("eai-sess"), LoginResult.cookies().get("UUkey")};
                    LoginRepository.WriteCookies(LoginResult.cookies().get("eai-sess"), LoginResult.cookies().get("UUkey"), getApplicationContext());
                    return "操作成功";
                }
                return body;
            } catch (Exception e) {
                Log.d("Cookie", "try err");
                Log.d("Cookie", e.toString());
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("操作成功")) {
                loginViewModel.setLoginResult(new Result.Success<>("操作成功!"));
            } else {
                loginViewModel.setLoginResult(new Result.Error(new IOException(result)));
            }
        }
    }
    //获得学期
    public class get_Term extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... voids) {
            String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
            String year = "2019-2020";
//        String year = myDateUtils.getYear();
//        String term = LoginRepository.getTerm(contexts[0]);
            String type = "2";
            Document mes;
            Log.d("data", year);
            Log.d("data", term);
            try {
                mes = Jsoup.connect(url)
                        .data("year", year)
                        .data("term", "2")
                        .data("week", "1")
                        .data("type", type)
                        .cookie("eai-sess", cookies[0])
                        .cookie("UUkey", cookies[1])
                        .ignoreContentType(true)
                        .post();
                Log.d("data", mes.toString());
                return mes;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... voids) {
            super.onProgressUpdate(voids);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Document result) {
            super.onPostExecute(result);
            if (result.body().text().contains("操作成功")) {
                term = LoginParser.findTerm(result, getApplicationContext());
            } else {
                term = "1";
            }
        }
    }
    //初始化课程数据库
    public class init_Classes extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... strings) {
            String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
            String type = "2";
            Document mes;
            String p;
            try {
                Log.d("data", "开始获取数据");
                for (int i = 1; i < 19; i++) {
                    mes = Jsoup.connect(url)
                            .data("year", year)
                            .data("term", term)
                            .data("week", Integer.toString(i))
                            .data("type", type)
                            .cookie("eai-sess", cookies[0])
                            .cookie("UUkey", cookies[1])
                            .ignoreContentType(true)
                            .post();
//                Log.d("data",mes.toString());
                    p = mes.body().text();
                    if (p.contains("操作成功")) {
                        publishProgress(new String[]{p,String.valueOf(i)});
                    } else {
                        return null;
                    }
                }
                return null;
            } catch (Exception e) {
//            Log.d("data",e.toString());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... strings){
            super.onProgressUpdate(strings);
//            Parser.parser(documents[0]);
            LoginParser.parse(strings,getApplicationContext(),logindbHelper);
        }


        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            //结束此activity
//            setResult(Activity.RESULT_OK);
            logindbHelper.close();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            finish();
            startActivity(intent);

        }
    }
}
