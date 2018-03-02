package com.theaty.peisongda.ui.mine;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.ui.home.utils.GlideCircleTransform;
import com.theaty.peisongda.ui.login.activity.LoginActivity;
import com.theaty.peisongda.ui.mine.activity.MyCodeActivity;

import app.AppManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import foundation.base.fragment.BaseFragment;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;

import static com.theaty.peisongda.notification.NotificationKey.MYINFO;


/**
 * Created by track on 2017/2/26.
 */

public class MineFragment extends BaseFragment implements NotificationListener{


    @BindView(R.id.member_avter_img)
    ImageView memberAvterImg;
    @BindView(R.id.member_name)
    TextView memberName;
    @BindView(R.id.my_info)
    LinearLayout myInfo;
    @BindView(R.id.about_us)
    LinearLayout aboutUs;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.feedback)
    LinearLayout feedback;
    @BindView(R.id.logout)
    LinearLayout logout;
    Unbinder unbinder;
    private MemberModel memberModel;


    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.mine_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("个人中心");
        initData();
        NotificationCenter.defaultCenter.addListener(MYINFO, this);

    }

    private void initData() {
        new MemberModel().member_info(DatasStore.getCurMember().key, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                memberModel = (MemberModel) o;
                if (memberModel.member_nick!=null) {
                    memberName.setText(memberModel.member_nick);
                }
                Glide.with(getActivity()).load(memberModel.member_avatar).transform(new GlideCircleTransform(getContext())).into(memberAvterImg);

            }

            @Override
            public void failed(ResultsModel resultsModel) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotificationCenter.defaultCenter.removeListener(MYINFO, this);
        unbinder.unbind();

    }


    @OnClick({ R.id.my_info, R.id.about_us, R.id.share, R.id.feedback, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.my_info:

                break;
            case R.id.about_us:
                new MemberModel().setting(new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {

                    }

                    @Override
                    public void successful(final Object o) {
                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                .setTitle("拨打客服电话?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + o.toString());
                                        intent.setData(data);
                                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
//                AboutUsActivity.into(getContext());
                break;
            case R.id.share:
                MyCodeActivity.into(getContext());
//                new UmengShareUtils((BaseActivity) getActivity()).shareByApp();
                break;
            case R.id.feedback:
//                FeedBackActivity.start(getContext());
                break;
            case R.id.logout:
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("确定要退出吗？ ")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                        //登出成功
                                        DatasStore.removeCurMember();
                                        AppManager.finishAllActivity();
                                        LoginActivity.into(getActivity());



                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();



                break;
        }
    }

    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals(MYINFO)) {
            initData();
            return true;
        }
        return false;
    }
}
