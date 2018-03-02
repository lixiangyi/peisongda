package com.theaty.peisongda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.system.UpdateManager;
import com.theaty.peisongda.ui.home.OrderFragmentNew;
import com.theaty.peisongda.ui.login.activity.LoginActivity;
import com.theaty.peisongda.ui.manager.ManagerFragment;
import com.theaty.peisongda.ui.mine.MineFragment;
import com.theaty.versionmanagerlibrary.VersionManager;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseFragmentActivity;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;
import foundation.util.DoubleClickExitUtils;

import static com.theaty.peisongda.notification.NotificationKey.filterMainActivity;
import static com.theaty.peisongda.notification.NotificationKey.filterPublic;

/**
 * 主页面
 */
public class MainActivity extends BaseFragmentActivity implements NotificationListener {

    @BindView(R.id.tabRL0)
    RelativeLayout tabRL0;
    @BindView(R.id.tabRL1)
    RelativeLayout tabRL1;
    @BindView(R.id.tabRL2)
    RelativeLayout tabRL2;


    @BindView(R.id.iv_0)
    ImageView iv_0;
    @BindView(R.id.iv_1)
    ImageView iv_1;
    @BindView(R.id.iv_2)
    ImageView iv_2;


    @BindView(R.id.tv_0)
    TextView tv_0;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.cicle_num_tv)
    TextView cicleNumTv;




    private DoubleClickExitUtils duClickExitHelper;
    public static int mSrceenWidth;
    public static int mSrceenHeight;

    private ArrayList<Class<? extends Fragment>> fragments;
    private boolean isLogin;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_main);
    }

    @Override
    protected ArrayList<Class<? extends Fragment>> fragmentClasses() {

        fragments = new ArrayList<>();
        fragments.add(OrderFragmentNew.class);
        fragments.add(ManagerFragment.class);
        fragments.add(MineFragment.class);

        return fragments;
    }

    @Override
    protected int containerViewId() {
        return R.id.contentFL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        getScreenWidth();
        //如果打开了蓝牙 并且缓存的蓝牙存在
//        if (ProbjectUtil.openBluetooth(MainActivity.this) && DatasStore.getCurBlueTooth() != null) {
//            ProbjectUtil.setPrintDataService(new PrintDataService());
//            if (!ProbjectUtil.getPrintDataService().connect()) {
//                ToastUtil.showToast("打印机连接失败");
//            }
//        }else {
//            new AlertDialog.Builder(this)
//                    .setTitle("提示")
//                    .setMessage("打印订单需要开启蓝牙并连接打印机才可以打印 ")
//                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            BluetoothActivity.into(MainActivity.this);
//                        }
//                    })
//                    .setNegativeButton("取消", null)
//                    .show();
//        }
//        setRefresh(0, true);//设置第二页可以点击刷新
        enableTabItem(0);
        VersionManager.getInstance(this, "http://"+ BaseModel.HOSTIP+"/mobile/UserHelp/mb_version_new");
        NotificationCenter.defaultCenter.addListener(filterMainActivity, this);
        duClickExitHelper = new DoubleClickExitUtils(this);
//
        updateApp();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static final String SYSTEM_TRIBE_CONVERSATION = "sysTribe";
    public static final String SYSTEM_FRIEND_REQ_CONVERSATION = "sysfrdreq";
    public static final String RELATED_ACCOUNT = "relatedAccount";

    /**
     * 自定义会话示例展示系统通知的示例
     */
    private void initCustomConversation() {
//        CustomConversationHelper.addCustomConversation(SYSTEM_FRIEND_REQ_CONVERSATION, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("**********", "onResume_mainActivity");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter.removeListener(filterMainActivity, this);
        VersionManager.getInstance().unregister();
    }

    private void updateApp() {
        UpdateManager.checkUpdate(this);
    }

    public void enableTabItem(int position) {
        if (0 > position || 2 < position)
            return;

        iv_0.setEnabled(false);
        iv_1.setEnabled(false);
        iv_2.setEnabled(false);

        tv_0.setEnabled(false);
        tv_1.setEnabled(false);
        tv_2.setEnabled(false);

        switch (position) {
            case 0:
                iv_0.setEnabled(true);
                tv_0.setEnabled(true);

                break;
            case 1:
                iv_1.setEnabled(true);
                tv_1.setEnabled(true);

                break;
            case 2:
                iv_2.setEnabled(true);
                tv_2.setEnabled(true);

                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return duClickExitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.tabRL0, R.id.tabRL1, R.id.tabRL2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabRL0:
                enableTabItem(0);
                selectPage(0);
                NotificationCenter.defaultCenter.postNotification(filterPublic);
                break;
            case R.id.tabRL1:
                if (DatasStore.isLogin()) {
                    enableTabItem(1);
                    selectPage(1);
                } else {
                    LoginActivity.into(this);

                }

                break;
            case R.id.tabRL2:
                if (DatasStore.isLogin()) {
                    enableTabItem(2);
                    selectPage(2);
                } else {
                    LoginActivity.into(this);

                }
                break;

        }
    }

    private void getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mSrceenWidth = metrics.widthPixels;
        mSrceenHeight = metrics.heightPixels;
    }


    @Override

    public boolean onNotification(Notification notification) {
        if (notification.key.equals(filterMainActivity)) {

            return true;
        }

        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}
