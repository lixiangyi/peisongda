package com.theaty.peisongda.ui.home.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.ui.home.utils.PrintUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import foundation.toast.ToastUtil;

public class PrintDataService {
    private String deviceAddress = null;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private BluetoothDevice device = null;
    private static BluetoothSocket bluetoothSocket = null;
    private static OutputStream outputStream = null;
    private static final UUID uuid = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean isConnection = false;
//    final String[] items = {"复位打印机", "标准ASCII字体", "压缩ASCII字体", "字体不放大",
//            "宽高加倍", "取消加粗模式", "选择加粗模式", "取消倒置打印", "选择倒置打印", "取消黑白反显", "选择黑白反显",
//            "取消顺时针旋转90°", "选择顺时针旋转90°"};
//    final byte[][] byteCommands = {{0x1b, 0x40},// 复位打印机
//            {0x1b, 0x4d, 0x00},// 标准ASCII字体
//            {0x1b, 0x4d, 0x01},// 压缩ASCII字体
//            {0x1d, 0x21, 0x00},// 字体不放大
//            {0x1d, 0x21, 0x11},// 宽高加倍
//            {0x1b, 0x45, 0x00},// 取消加粗模式
//            {0x1b, 0x45, 0x01},//选择加粗模式
//            {0x1b, 0x7b, 0x00},//取消倒置打印
//            {0x1b, 0x7b, 0x01},//选择倒置打印
//            {0x1d, 0x42, 0x00},//取消黑白反显
//            {0x1d, 0x42, 0x01},//选择黑白反显
//            {0x1b, 0x56, 0x00},//取消顺时针旋转90°
//            {0x1b, 0x56, 0x01},//选择顺时针旋转90°
//    };

    public PrintDataService(String deviceAddress) {
        super();
        this.deviceAddress = deviceAddress;
        this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
    }

    public PrintDataService() {
        super();
        this.deviceAddress = DatasStore.getCurBlueTooth().getAddress();
        this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
    }

    /**
     * 获取设备名称
     *
     * @return String
     */
    public String getDeviceName() {
        return this.device.getName();
    }

    /**
     * 连接蓝牙设备
     */
    public boolean connect() {
        if (!this.isConnection) {
            try {

                bluetoothSocket = this.device
                        .createRfcommSocketToServiceRecord(uuid);

                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                PrintUtils.setOutputStream(outputStream);
                this.isConnection = true;
                if (this.bluetoothAdapter.isDiscovering()) {
                    ToastUtil.showToast("关闭适配器");
                    this.bluetoothAdapter.isDiscovering();
                }
            } catch (Exception e) {
                ToastUtil.showToast("打印机连接失败");
                return false;
            }
            ToastUtil.showToast("打印机连接成功");
            DatasStore.setCurBlueTooth(this.device);
            return true;
        } else {
            return true;
        }
    }

    public void connectAsy(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isConnection) {
                    try {
                        long st = System.nanoTime();
                        long timeOut = 3000000000L;//3秒
//                        Message message = new Message();
//                        message.what = 0x125;
//                        handler.sendMessageDelayed(message,3000);
                        if (bluetoothSocket==null||!bluetoothSocket.isConnected()) {
                            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
                            bluetoothSocket.connect();
                        }
                        System.out.println("***取出数据" + (System.nanoTime() - st));//微毫秒级别，小数点往前看9位
//                        if ((System.nanoTime() - st) < timeOut) {
//                            handler.removeMessages(0x125);
//                        }

                    } catch (Exception e) {
//                        if (e.getMessage().equals("read failed, socket might closed or timeout, read ret: -1")) {
//                            try {
////                                ToastUtil.showToast(e.getMessage());
                                Log.e("mmmmmmm","链接异常:"+e.getMessage());
//                                bluetoothSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
//                                bluetoothSocket.connect();
//                            } catch (Exception e2) {
//                                Log.e("mmmmmmm", "链接异常2:"+e2.getMessage());
//                                ToastUtil.showToast("打印机连接失败");
//                                Message message = new Message();
//                                message.what = 0x125;
//                                handler.sendMessage(message);
//                                return;
//                            }
//                        } else {
                            ToastUtil.showToast("打印机连接失败");
                            Message message = new Message();
                            message.what = 0x125;
                            handler.sendMessage(message);
                            return;
//                        }
                    }
                    try {
                        outputStream = bluetoothSocket.getOutputStream();
                    } catch (IOException e) {
                        Log.e("mmmmmmm", "获取流异常:"+e.getMessage());
                        e.printStackTrace();
                    }
                    PrintUtils.setOutputStream(outputStream);
                    isConnection = true;
                    if (bluetoothAdapter.isDiscovering()) {
                        ToastUtil.showToast("关闭适配器");
                        bluetoothAdapter.isDiscovering();
                    }

                    ToastUtil.showToast("打印机连接成功");
                    DatasStore.setCurBlueTooth(device);
                    Message message = new Message();
                    message.what = 0x124;
                    handler.sendMessage(message);
                } else {

                }

            }
        }).start();


    }

    /**
     * 断开蓝牙设备连接
     */
    public static void disconnect() {
        try {
            bluetoothSocket.close();
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

//    /**
//     * 选择指令
//     */
//    public void selectCommand() {
//        new AlertDialog.Builder(context).setTitle("请选择指令")
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (isConnection) {
//                            try {
//
//                                outputStream.write(byteCommands[which]);
//
//                            } catch (IOException e) {
//                                Toast.makeText(context, "设置指令失败！",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(context, "设备未连接，请重新连接！",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).create().show();
//    }

    /**
     * 发送数据
     */
    public boolean send(String sendData) {
        if (this.isConnection) {
            ToastUtil.showToast("开始打印");
            try {
                byte[] data = sendData.getBytes("gbk");
                outputStream.write(data, 0, data.length);
                outputStream.flush();
                return true;
            } catch (IOException e) {
//                ToastUtil.showToast(e);
                Log.e("mmmmmmm","发送数据打印:"+e.getMessage());
                isConnection = false;
                disconnect();
                ToastUtil.showToast("发送失败");
                return false;

            }
        } else {
            ToastUtil.showToast("设备未连接，请重新连接！");
            isConnection = false;
            return false;
        }
    }

}