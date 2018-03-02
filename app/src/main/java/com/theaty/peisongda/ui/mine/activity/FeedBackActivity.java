package com.theaty.peisongda.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseActivity;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.bt_feedback)
    Button btFeedback;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_feed_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("意见反馈");
        registerBack();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FeedBackActivity.class);
        context.startActivity(starter);
    }

    @OnClick(R.id.bt_feedback)
    public void onViewClicked() {
        if(TextUtils.isEmpty(etFeedback.getText().toString().trim())){
            showToast("反馈内容不能为空");
            return;
        }
        new MemberModel().feedback_add(etFeedback.getText().toString().trim(), new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();

            }

            @Override
            public void successful(Object o) {
                hideLoading(o.toString());
                finish();
//                MemberModel memberModel = (MemberModel) o;
//                ToastUtil.showToast(o.toString());
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg());
//                ToastUtil.showToast(resultsModel.getErrorMsg());
            }
        });
    }
}
