package com.theaty.peisongda.ui.home;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.GoodsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.model.peisongda.OrderModel;
import com.theaty.peisongda.system.AppUtils;
import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.ui.home.Activity.BluetoothActivity;
import com.theaty.peisongda.ui.home.Activity.SearchActivity;
import com.theaty.peisongda.ui.home.Activity.SumActivity;
import com.theaty.peisongda.ui.home.Adapter.OrderAdapter;
import com.theaty.peisongda.ui.home.service.PrintDataService;
import com.theaty.peisongda.ui.home.utils.PrintContract;
import com.theaty.peisongda.ui.home.utils.ProbjectUtil;
import com.theaty.peisongda.ui.login.activity.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import foundation.base.fragment.BaseFragment;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;
import foundation.toast.ToastUtil;
import foundation.util.ScreenUtils;
import foundation.widget.swiperefresh.SuperSwipeRefreshLayout;

import static com.theaty.peisongda.notification.NotificationKey.filterPublic;


/**
 * Created by li on 2017/2/26.
 */

public class OrderDoneFragment extends BaseFragment implements View.OnClickListener, NotificationListener {

    private static final int REQUEST_ENABLE_BT = 1;
    @BindView(R.id.search_view)
    LinearLayout searchView;
    @BindView(R.id.dating_list)
    RecyclerView datingList;
    @BindView(R.id.swipe_layout_home)
    SuperSwipeRefreshLayout swipeLayoutHome;
    @BindView(R.id.fabu)
    TextView fabu;
    Unbinder unbinder;
    View mEmptyView;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    private int curpage = 1;
    OrderAdapter listAdapter;
    private ArrayList<OrderModel> OrderModels = new ArrayList<>();
    private ArrayList<OrderModel> OrderModelsSum = new ArrayList<>();
    private int lastOffset;
    private int lastPosition;
    private boolean isShow = false;
    private PopupWindow popupWindow;
    StringBuilder sb;
    UsbDevice usbDevice;
    UsbManager usbManager;
    String ids;
    private String contentall;


    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.home_fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initView();
        getdata(1);
        NotificationCenter.defaultCenter.addListener(filterPublic, this);
    }

    @Override
    protected void goNext() {
        super.goNext();
        if (isShow) {
            popupWindow.dismiss();
            isShow = false;
        } else {
            showPopupWindow();
        }
    }

    private void showPopupWindow() {

        BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(getActivity()).inflate(R.layout.pop_add_layout, null);
        bubbleLayout.findViewById(R.id.add_pop_statistics).setOnClickListener(this);
        bubbleLayout.findViewById(R.id.add_pop_delete).setOnClickListener(this);
        popupWindow = BubblePopupHelper.create(getActivity(), bubbleLayout);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        popupWindow.showAtLocation(getRightImage(), Gravity.NO_GRAVITY, width - ScreenUtils.dip2px(108), ScreenUtils.dip2px(75));

        isShow = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_pop_statistics:
                popupWindow.dismiss();
                new MemberModel().empty_order(new BaseModel.BaseModelIB() {
                    @Override
                    public void StartOp() {
                        showLoading();
                    }

                    @Override
                    public void successful(Object o) {
                        getdata(1);
                        hideLoading();
                    }

                    @Override
                    public void failed(ResultsModel resultsModel) {
                        ToastUtil.showToast(resultsModel.getErrorMsg());
                        hideLoading();

                    }
                });
                break;

            case R.id.add_pop_delete:
                popupWindow.dismiss();
                compute();
                break;
        }

    }

    private void compute() {
        ArrayList<GoodsModel> g = new ArrayList<GoodsModel>();
        if (OrderModelsSum.size() > 0) {
            HashMap<Integer, GoodsModel> map = new HashMap<Integer, GoodsModel>();
            for (int i = 0; i < OrderModelsSum.size(); i++) {
                for (int j = 0; j < OrderModelsSum.get(i).extend_order_goods.size(); j++) {
                    if (map.get(OrderModelsSum.get(i).extend_order_goods.get(j).goods_id) == null) {
                        map.put(OrderModelsSum.get(i).extend_order_goods.get(j).goods_id,
                                OrderModelsSum.get(i).extend_order_goods.get(j));
                    } else {
                        GoodsModel goodsmodel = new GoodsModel();
                        goodsmodel = map.get(OrderModelsSum.get(i).extend_order_goods.get(j).goods_id);
                        goodsmodel.goods_num = map.get(OrderModelsSum.get(i).extend_order_goods.get(j).goods_id).goods_num + OrderModelsSum.get(i).extend_order_goods.get(j).goods_num;
                        map.put(OrderModelsSum.get(i).extend_order_goods.get(j).goods_id, goodsmodel);
                    }
                }

            }
//            String ss = "";
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
                Object val = entry.getValue();
                g.add((GoodsModel) val);
//                ss+=" "+((GoodsModel)val).goods_name+"   "+((GoodsModel)val).goods_num+"\n\n";
            }
//            ToastUtil.showToast(ss);
            SumActivity.start(getActivity(), g);

        } else {
            ToastUtil.showToast("没有订单");
        }

    }

    private String print() {
        ids = "";
        String ss = "";
        ss = "送菜打印" + "\n\n" + "\n\n" +
                "******************************" + "\n\n" +
                "订单号：" + "900000000011301" + "\n\n" +
                "收货人：" + "老王餐馆" + "\n\n" +
                "订单时间：" + "2018-02-04 16:21:46" + "\n\n"
                + "订单地址：" + "北京市 海淀区 西四环 xx小区 xxxx" + "\n\n" +
                "******************************" + "\n\n"
                + "菜名称" + "      " + "数量" + "      " + "单价" + "\n\n"
                + "胡萝卜" + "      " + 5 + "斤左右" + "    " + "1.2元" + "\n\n"
                + "大白菜" + "      " + 8 + "斤左右" + "   " + "2.2元" + "\n\n"
                + "芹菜  " + "      " + 10 + "斤左右" + "   " + "3.2元" + "\n\n"
                + "青椒  " + "      " + 3 + "斤左右" + "   " + "4.2元" + "\n\n"
                + "西红柿" + "      " + 4 + "斤左右" + "   " + "5.2元" + "\n\n+" +
                "******************************" + "\n\n"
        ;
        return ss;
    }


    void initView() {

        swipeLayoutHome.setDirection(SuperSwipeRefreshLayout.Direction.BOTH);
        swipeLayoutHome.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (SuperSwipeRefreshLayout.Direction.TOP == direction) {
                    curpage = 1;
                    getdata(curpage);
                    listAdapter.notifyDataSetChanged();


                } else if (SuperSwipeRefreshLayout.Direction.BOTTOM == direction) {
                    curpage++;
                    getdata(curpage);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        datingList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new OrderAdapter(getActivity(), OrderModels);
        listAdapter.setOnItemClickLitener(new OrderAdapter.OnItemClickLitener() {


            @Override
            public void onItemClick(View view, OrderModel orderModel) {
                final String ss = String.valueOf(PrintContract.createXxTxt(orderModel, 2));
                tryPrint(ss);
            }

            @Override
            public void onChange(ArrayList<OrderModel> Models) {
                OrderModelsSum = Models;
            }


        });

    }





    void getdata(final int curpage) {

        new MemberModel().order_list(20, curpage, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                hideLoading();
                OrderModels = (ArrayList<OrderModel>) o;
                if (OrderModels == null || OrderModels.size() == 0) {
                    if (swipeLayoutHome != null) {
                        swipeLayoutHome.setRefreshing(false);
                    }

                    if (curpage > 1) {
                        ToastUtil.showToast("没有更多数据了！");
                    }
                    OrderModels = new ArrayList<OrderModel>();
                }
                if (null == listAdapter) {
                    listAdapter = new OrderAdapter(getActivity(), OrderModels);
                    datingList.setAdapter(listAdapter);
                } else {
                    if (curpage == 1) {
                        // datingList.setAdapter(listAdapter);

                        listAdapter.upDate(OrderModels);
                        datingList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
//                        datingList.scrollToPosition(listAdapter.getItemCount() - 1);
                    } else {
                        listAdapter.addDate(OrderModels);
                        listAdapter.notifyDataSetChanged();
//


                    }
                }
                if (swipeLayoutHome != null) {
                    swipeLayoutHome.setRefreshing(false);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading();
                if (swipeLayoutHome != null) {
                    swipeLayoutHome.setRefreshing(false);
                }
                ToastUtil.showToast(resultsModel.getErrorMsg());
            }


        });
    }

    void getdata1(final int curpage) {

        new MemberModel().order_list(20, curpage, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                OrderModels = (ArrayList<OrderModel>) o;
                if (OrderModels == null || OrderModels.size() == 0) {
                    if (swipeLayoutHome != null) {
                        swipeLayoutHome.setRefreshing(false);
                    }

                    if (curpage > 1) {
                        ToastUtil.showToast("没有更多数据了！");
                    }
                    OrderModels = new ArrayList<OrderModel>();
                }
                if (null == listAdapter) {
                    listAdapter = new OrderAdapter(getActivity(), OrderModels);
                    datingList.setAdapter(listAdapter);
                } else {
                    if (curpage == 1) {
                        // datingList.setAdapter(listAdapter);

                        listAdapter.upDate(OrderModels);
                        datingList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
//                        datingList.scrollToPosition(listAdapter.getItemCount() - 1);
                    } else {
                        listAdapter.addDate(OrderModels);
                        listAdapter.notifyDataSetChanged();
//


                    }
                }
                if (swipeLayoutHome != null) {
                    swipeLayoutHome.setRefreshing(false);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading();
                if (swipeLayoutHome != null) {
                    swipeLayoutHome.setRefreshing(false);
                }
                ToastUtil.showToast(resultsModel.getErrorMsg());
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        NotificationCenter.defaultCenter.removeListener(filterPublic, this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.search_view, R.id.fabu})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        switch (view.getId()) {
            case R.id.search_view:
//
                getActivity().startActivity(intent);
//
                break;
            case R.id.fabu:
                if (DatasStore.isLogin()) {
//                    ToastUtil.showToast(print());
                    StringBuilder sb = new StringBuilder();
                    sb.append(PrintContract.createXxTxt("sss"));
//                    sb.append(PrintContract.createXxTxt("sss"));
                    final String ss = String.valueOf(sb);
                    tryPrint(ss);


//                    usbPrint(print());
//                    PublishActivity.start(getContext());
                } else {
                    LoginActivity.into(getActivity());
                }
                break;
            case R.id.search_icon:

                getActivity().startActivity(intent);
//
                break;

        }
    }

    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals(filterPublic)) {
            getdata1(1);
            return true;
        }
        return false;
    }

    //尝试打印
    private void tryPrint(String content) {
        StringBuilder warning = new StringBuilder();
        String btWarning;//蓝牙打印log
        ;//首先usb打印
        String usbWarning = usbPrint(content);
//        btPrint(content);
        if (usbWarning.equals("yes")) {//usb 打印成功
            return;
        } else if ((btWarning = btPrint(content)).equals("yes")) {
            return;
        } else {
            warning.append(usbWarning);
            warning.append(btWarning);
            AppUtils.showDialog("提示", warning.toString(), this.getContext());// 显示log提醒
        }


    }
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0x124:
                    if (ProbjectUtil.Print(getActivity(), contentall)) {
                        hideLoading();
                    } else {
                          hideLoading();
                        showBluetoothDialog("打印机打印失败");
                    }
                    break;
                case 0x125:
                    hideLoading();
                    showBluetoothDialog(" 蓝牙打印机未开启或者打印机故障");

                    break;

            }
        }
    };

    //usb打印成功返回 “yes” 失败返回原因
    private String usbPrint(String content) {
        StringBuilder rsb = new StringBuilder();
        rsb.append("开始检测USB打印机\n");
        boolean isSupportOtg = this.getContext().getPackageManager().hasSystemFeature((PackageManager.FEATURE_USB_HOST));
        if (isSupportOtg) {// 支持otg
            rsb.append("手机支持OTG\n");
        } else {
            rsb.append("手机不支持OTG\n");
            return rsb.toString();
        }


        sb = new StringBuilder();
        usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> map = usbManager.getDeviceList();
        rsb.append("检测到" + map.size() + "个外设\n");
        if (!map.isEmpty()) {
            for (UsbDevice device : map.values()) {
                int VendorID = device.getVendorId();
                int ProductID = device.getProductId();
                sb.append("VendorID:" + VendorID + ",ProductID:" + ProductID + "\n");
                if (VendorID == 10473 && ProductID == 649) {//???咱们的所有打印机
                    usbDevice = device;

                    if (!usbManager.hasPermission(usbDevice)) {
                        rsb.append("没有权限操作USB设备\n");
                        return rsb.toString();
                    }

                    sb.append("设备接口个数：" + usbDevice.getInterfaceCount() + "\n");
                    UsbInterface usbInterface = usbDevice.getInterface(0);

                    sb.append("分配端点个数：" + usbInterface.getEndpointCount() + "\n");
                    UsbEndpoint outEndpoint = usbInterface.getEndpoint(0);

                    UsbDeviceConnection connection = usbManager.openDevice(usbDevice);
                    connection.claimInterface(usbInterface, true);

                    // 打印数据
                    content = content + "\n\n\n\n\n\n";
                    byte[] printData = null;
                    try {
                        printData = content.getBytes("gbk");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    int out = connection.bulkTransfer(outEndpoint, printData, printData.length, 30000);
                    // 关闭连接
                    connection.close();
                    return "yes";
                }
            }
            rsb.append("从" + map.size() + "个设备中没有匹配到USB打印机\n");
            return rsb.toString();
//            usbtestTvInfo.setText(sb.toString());
        } else {
            rsb.append("没有检测到USB外设\n");
            return rsb.toString();
//            Toast.makeText(getActivity(), "没有找到打印机！", Toast.LENGTH_SHORT).show();
//            usbtestTvInfo.setText("设备为空");
        }

    }

    //蓝牙打印成功返回 “yes” 失败返回原因
    private String btPrint(String content) {

        contentall = content;
        StringBuilder rsb = new StringBuilder();
        rsb.append("：：：：：开始尝试蓝牙打印！\n");
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
//        如果BluetoothAdapter 为null，说明android手机没有蓝牙模块。
//        判断蓝牙模块是否开启，blueadapter.isEnabled() true表示已经开启，false表示蓝牙并没启用。
        if (blueadapter == null) {
            rsb.append("手机没有蓝牙模块！\n");
            return rsb.toString();
        } else {
            rsb.append("检测到蓝牙模块！\n");
            if (blueadapter.isEnabled()) {
                rsb.append("手机蓝牙为开启状态！\n");
//                TODO if(蓝牙打印成功)return “yes”
                   //有效代码
//                if (DatasStore.getCurBlueTooth() == null) {
//                    showBluetoothDialog("");
//                    return "yes";
//                } else {
//                    ProbjectUtil.setPrintDataService(new PrintDataService());
//                    if (ProbjectUtil.getPrintDataService().connect()) {
//                        if (ProbjectUtil.Print(getActivity(), content)) {
//                            hideLoading();
//                            return "yes";
//                        } else {
//                            rsb.append("打印机打印失败\n");
//                            showBluetoothDialog("打印机打印失败");
//                            return "yes";
//                        }
//                    } else {
//                        showBluetoothDialog(" 蓝牙打印机未开启或者打印机故障");
////                    蓝牙打印机未开启或者打印机故障，去设置
//                        rsb.append("打印机连接失败\n");
//                        return "yes";
//                    }
//                }

                //测试代码
                if (DatasStore.getCurBlueTooth() == null) {
                    showBluetoothDialog("");
                    return "yes";
                } else {
                    showLoading("正在连接。。。");
                    ProbjectUtil.setPrintDataService(new PrintDataService());
                    ProbjectUtil.getPrintDataService().connectAsy(handler);
                }

            } else {

                rsb.append("手机没有开启蓝牙！");
                showBluetoothDialog(" 手机没有开启蓝牙！");
                return "yes";
            }

            return  "yes";
//
//                if (ProbjectUtil.getPrintDataService() == null|| !ProbjectUtil.getPrintDataService().connect()) {
//
//                    showBluetoothDialog(" 蓝牙打印机未开启或者打印机故障，去设置");
////                    蓝牙打印机未开启或者打印机故障，去设置
//                    rsb.append("打印机连接失败\n");
//                    // ProbjectUtil.showBluetoothDialog(getActivity());
//                } else {
//                    if (ProbjectUtil.Print(getActivity(), content)) {
//                        hideLoading();
//                        return "yes";
//                    } else {
////                ToastUtil.showToast("请重新设置打印机");
//                        rsb.append("打印机打印失败\n");
//                        hideLoading();
//                        showBluetoothDialog("打印机打印失败");
//                        return "yes";
//                    }
//                }
//            } else {
//                rsb.append("手机没有开启蓝牙！");
//            }
        }
    }

    void showBluetoothDialog(String ss) {
        if (ss.length() > 0) {

        } else {
            ss = "打印订单需要开启蓝牙并连接打印机才可以打印";
        }
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage(ss)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BluetoothActivity.into(getActivity());
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

}