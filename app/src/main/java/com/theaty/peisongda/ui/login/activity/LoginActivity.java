package com.theaty.peisongda.ui.login.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseActivity;
import foundation.toast.ToastUtil;
import foundation.util.PhoneUtils;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_phonenum)
    EditText loginPhonenum;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.login_login)
    TextView loginLogin;
    @BindView(R.id.kefu)
    TextView kefu;

    String phoneNum, verCode;
    private TimeCount time;


    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_login);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setTitle("登陆");

    }

    @OnClick({R.id.getCode, R.id.login_login, R.id.kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                phoneNum = loginPhonenum.getText().toString().trim();
                if (PhoneUtils.isMobileNO(phoneNum)) {
                    getVerificationCode();
                } else {
                    ToastUtil.showToast("手机号格式错误");
                }
                break;
            case R.id.login_login:
//                if (StringUtil.isEmpty(loginPhonenum.getText().toString().trim()) || StringUtil.isEmpty(loginPassword.getText().toString().trim())) {
//                    ToastUtil.showToast("手机号或密码输入错误");
//                } else if (!PhoneUtils.isMobileNO(loginPhonenum.getText().toString().trim())) {
//                    ToastUtil.showToast("手机号格式错误");
//                } else {
                postLoginData();
//                }
                break;
            case R.id.kefu:
                new MemberModel().setting(new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {

                    }

                    @Override
                    public void successful(final Object o) {
                        new android.support.v7.app.AlertDialog.Builder(LoginActivity.this)
                                .setTitle("拨打客服电话?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + o.toString());
                                        intent.setData(data);
                                        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }

                                        startActivity(intent);
                                    }
                                }).show();

                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {

                    }
                });
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getVerificationCode() {
        new MemberModel().login_identifycode(phoneNum, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                verCode = (String) o;
                ToastUtil.showToast("发送成功");
                //完成验证码请求，验证码按钮控件开始计时
                time = new TimeCount(60000, 1000);
                time.start();// 开始计时
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                ToastUtil.showToast(resultsModel.getErrorMsg());
                hideLoading();
            }
        });
    }

    private void postLoginData() {
        new MemberModel().Login(
                loginPhonenum.getText().toString().trim(),
                loginPassword.getText().toString().trim(),
                new BaseModel.BaseModelIB() {

                    @Override
                    public void StartOp() {
                        showLoading();
                    }

                    @Override
                    public void successful(Object o) {
                        hideLoading();

                        MemberModel memberModel = (MemberModel) o;
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {
                        ToastUtil.showToast(resultsModel.getErrorMsg());
                        hideLoading();
                    }
                });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            getCode.setText("获取验证码");
            getCode.setClickable(true);
            getCode.setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            getCode.setClickable(false);//防止重复点击
            getCode.setText("验证码 (" + millisUntilFinished / 1000 + "s)");
            getCode.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public static void into(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

}
