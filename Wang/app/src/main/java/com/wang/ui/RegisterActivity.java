package com.wang.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wang.R;
import com.wang.base.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.steamcrafted.loadtoast.LoadToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by 28724 on 2018/3/29.
 */

public class RegisterActivity extends AppCompatActivity {

    /*创建一个注册TAG*/
    private final static String REGISETR_TAG = "Register ===> ";

    /*注册所用URL*/
    private static final String url = "http://16n39847j2.51mypc.cn:16129/register";
    @BindView(R.id.et_re_username)
    EditText etReUsername;
    @BindView(R.id.et_re_password)
    EditText etRePassword;
    @BindView(R.id.et_re_email)
    EditText etReEmail;
    @BindView(R.id.sure_button)
    Button sureButton;

    private Handler ltHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        /*UI控件LoadToast*/
        final LoadToast lt;
        lt = new LoadToast(this).setProgressColor(Color.RED).setText("注册中").setTranslationY(600);
        View v = new View(this);
        v.setBackgroundColor(Color.RED);


        /*利用Handler进行UI更新*/
        ltHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: {
                        lt.success();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException E) {
                                }
                            }
                        }).start();
                        RegisterActivity.this.finish();
                        break;
                    }
                    case 1: {
                        lt.error();
                        break;
                    }
                    case 2: {
                        lt.show();
                        break;
                    }
                }
            }
        };
    }


    @OnClick({R.id.sure_button})
    public void OnClick(View view) {

//        if (TextUtils.isEmpty(etReUsername.getText())) {
//            etReUsername.setError("请输入用户名");
//        } else if (TextUtils.isEmpty(etRePassword.getText())) {
//            etRePassword.setError("请输入密码");
//        } else if (TextUtils.isEmpty(etReEmail.getText())) {
//            etReEmail.setError("请输入邮箱");
//        } else {
              Log.i(REGISETR_TAG, "用户确认并提交了信息");
             /*将Message送到Handler进行UI更新*/
              Message msg = new Message();
              msg.what = 2;
              ltHandler.sendMessage(msg);
             // Gson gson = new Gson();
              User user = new User("wang1111","12342226","wang1111");
              OkHttpUtils
                    .postString()
                    .url(url)
                    .content(new Gson().toJson(user))
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("boolean_context", "Error");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            int str = Integer.parseInt(response);
                            Log.i("tian", str+"");
                            Log.i("boolean_context", "success");
                                    /*将Message送到Handler进行UI更新*/
                            if(str==1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Message msg = new Message();
                                msg.what = 0;
                                ltHandler.sendMessage(msg);
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "注册失败，请重新注册", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Message msg = new Message();
                                msg.what = 1;
                                ltHandler.sendMessage(msg);
                            }
                        }
                    });
        }
//    }

}
