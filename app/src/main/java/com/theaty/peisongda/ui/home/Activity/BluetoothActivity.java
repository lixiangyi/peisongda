package com.theaty.peisongda.ui.home.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.theaty.peisongda.R;
import com.theaty.peisongda.databinding.BluetoothLayoutBinding;
import com.theaty.peisongda.databinding.ItemBluetoothBinding;
import com.theaty.peisongda.ui.home.Adapter.IViewDataRecyclerAdapter;
import com.theaty.peisongda.ui.home.service.PrintDataService;
import com.theaty.peisongda.ui.home.utils.ProbjectUtil;

import java.lang.reflect.Method;

import foundation.base.activity.BaseActivity;
import foundation.toast.ToastUtil;
import foundation.widget.recyclerView.SpaceItemDecoration;


public class BluetoothActivity extends BaseActivity implements View.OnClickListener {


    BluetoothLayoutBinding binding;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    BlueTooThAdapter blueTooThAdapter1;//已连接的
    BlueTooThAdapter blueTooThAdapter2;//未连接的
    boolean cancel;

    @Override
    protected View onCreateContentView() {
        binding = BluetoothLayoutBinding.inflate(getLayoutInflater(), _containerLayout, false);
        return binding.getRoot();

    }

    @Override
    protected void onPostInject() {
        super.onPostInject();
        setTitle("连接打印机");
        registerBack();
        binding.setOpen(false);

        binding.setOnClickListener(this);
        binding.recycler1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler1.addItemDecoration(new SpaceItemDecoration(2, false));
        blueTooThAdapter1 = new BlueTooThAdapter(true);
        binding.recycler1.setAdapter(blueTooThAdapter1);
        binding.recycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler2.addItemDecoration(new SpaceItemDecoration(2, false));
        blueTooThAdapter2 = new BlueTooThAdapter(false);
        binding.recycler2.setAdapter(blueTooThAdapter2);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
        if (bluetoothAdapter.isEnabled()) {
            binding.setOpen(true);
        }

    }
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0x124:
                     hideLoading();
                    finish();
                    break;
                case 0x125:
                    hideLoading();
                    ToastUtil.showToast("打印机连接出现故障，请重试");

                    break;

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ooopen:
                if (binding.getOpen()) {
                    bluetoothAdapter.disable();
                } else {
                    bluetoothAdapter.enable();
                }
                //   binding.setOpen(!binding.getOpen());
                break;
            case R.id.searchDevices:
                if (!binding.getOpen()) {
                    showToast("请开启蓝牙");
                    return;
                }
                cancel = false;
                blueTooThAdapter1.clear();
                blueTooThAdapter2.clear();
                blueTooThAdapter1.notifyDataSetChanged();
                blueTooThAdapter2.notifyDataSetChanged();
                bluetoothAdapter.startDiscovery();
                break;
        }
    }

    /**
     * 蓝牙广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        ProgressDialog progressDialog = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (cancel)
                    return;
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device == null || device.getName() == null)
                    return;
                if (blueTooThAdapter1.getInfos().contains(device) || blueTooThAdapter2.getInfos().contains(device))
                    return;
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    //添加到 已连接的
                    if (!blueTooThAdapter1.getInfos().contains(device)) {
                        blueTooThAdapter1.addInfo(device);
                        blueTooThAdapter1.notifyDataSetChanged();
                    }
                } else {
                    //添加到 未连接的
                    if (!blueTooThAdapter2.getInfos().contains(device)) {
                        blueTooThAdapter2.addInfo(device);
                        blueTooThAdapter2.notifyDataSetChanged();
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                progressDialog = ProgressDialog.show(context, "请稍等...",
                        "搜索蓝牙设备中...", true);
                progressDialog.setCancelable(true);
                cancel = false;
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        bluetoothAdapter.cancelDiscovery();
                        cancel = true;
                    }
                });
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                if(progressDialog != null){
                    progressDialog.dismiss();
                }
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {

                if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    binding.setOpen(true);
                    cancel = false;
                    blueTooThAdapter1.clear();
                    blueTooThAdapter2.clear();
                    blueTooThAdapter1.notifyDataSetChanged();
                    blueTooThAdapter2.notifyDataSetChanged();
                    bluetoothAdapter.startDiscovery();
                } else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    ProbjectUtil.setPrintDataService(null);
                    binding.setOpen(false);
                    blueTooThAdapter1.clear();
                    blueTooThAdapter1.notifyDataSetChanged();
                    blueTooThAdapter2.clear();
                    blueTooThAdapter2.notifyDataSetChanged();

                }
            }

        }
    };

    public class BlueTooThAdapter extends IViewDataRecyclerAdapter<BluetoothDevice, ItemBluetoothBinding> {
        boolean connect;

        public BlueTooThAdapter(boolean connect) {
            this.connect = connect;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_bluetooth;
        }

        @Override
        protected void bindData(ItemBluetoothBinding itemBluetoothBinding, final BluetoothDevice info, final int position) {
            itemBluetoothBinding.name.setText(info.getName() + "");
            itemBluetoothBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (connect) {
                        // 让 ProbjectUtil 持有
                        showLoading();
                        ProbjectUtil.setPrintDataService(new PrintDataService(info.getAddress()));
                        ProbjectUtil.getPrintDataService().connectAsy(handler);
//                        finish();
                    } else {
                        try {
                            showLoading();
                            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                            createBondMethod.invoke(info);
                            blueTooThAdapter1.addInfo(info);
                            blueTooThAdapter1.notifyDataSetChanged();
                            removeIndex(position);
                            hideLoading();
                        } catch (Exception e) {
                            showToast("配对失败");
                            hideLoading();
                        }
                    }
                }
            });
        }
    }

    public static void into(Context activity) {
        activity.startActivity(new Intent(activity, BluetoothActivity.class));
    }
}
