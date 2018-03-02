package com.theaty.peisongda.ui.home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.model.peisongda.OrderModel;
import com.theaty.peisongda.ui.home.Adapter.OrderDetialAdapter;
import com.theaty.peisongda.ui.home.utils.ArithUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import foundation.base.activity.BaseActivity;
import foundation.notification.NotificationCenter;
import foundation.toast.ToastUtil;

import static com.theaty.peisongda.notification.NotificationKey.ChangeTab;
import static com.theaty.peisongda.notification.NotificationKey.filterPublic;

public class OrderDetialActivity extends BaseActivity {

    @BindView(R.id.order_num_detial)
    TextView orderNumDetial;
    @BindView(R.id.name_order_detial)
    TextView nameOrderDetial;
    @BindView(R.id.phone_order_detial)
    TextView phoneOrderDetial;
    @BindView(R.id.address_detial)
    TextView addressDetial;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.time1)
    TextView time1;
    @BindView(R.id.total_price)
    TextView totalPrice_tv;
    private OrderModel ordermodel;
    private OrderDetialAdapter listAdapter;
    private double totalPrice;
    private String ids;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_order_detial);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBack();
        setTitle("处理订单");
        setRightTitle("完成");
        ordermodel = (OrderModel) getIntent().getSerializableExtra("order");
        initView();
        computeTotalPrice();
    }


    public static void into(Context context, OrderModel order) {
        Intent starter = new Intent(context, OrderDetialActivity.class);
        starter.putExtra("order", order);
        context.startActivity(starter);
    }

    @Override
    protected void goNext() {
        super.goNext();
        complete();
    }

    private void complete() {
        ids = "";
        for (int i = 0; i < ordermodel.extend_order_goods.size(); i++) {
            ids += ordermodel.extend_order_goods.get(i).rec_id + "|" + ordermodel.extend_order_goods.get(i).goods_num +"|"
                    + ordermodel.extend_order_goods.get(i).goods_price + ",";
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        new MemberModel().order_gn_change(ids,ordermodel.order_id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                ToastUtil.showToast(o.toString());
                NotificationCenter.defaultCenter.postNotification(filterPublic);
                NotificationCenter.defaultCenter.postNotification(ChangeTab);
                finish();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg());

            }
        });
    }

    private void initView() {
        orderNumDetial.setText(ordermodel.order_sn);
        time1.setText(ordermodel.add_time);
        nameOrderDetial.setText(ordermodel.reciver_info.reciver_name);
        phoneOrderDetial.setText(ordermodel.reciver_info.mob_phone);
        addressDetial.setText(ordermodel.reciver_info.address);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new OrderDetialAdapter(this, ordermodel.extend_order_goods);
        recycler.setAdapter(listAdapter);
        listAdapter.setOnItemClickLitener(new OrderDetialAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(double d) {
                computeTotalPrice();
            }


        });
    }

    private void computeTotalPrice() {
        totalPrice = 0;
        ids = "";
        for (int i = 0; i < ordermodel.extend_order_goods.size(); i++) {
            totalPrice = ArithUtil.add(totalPrice, ArithUtil.mul(ordermodel.extend_order_goods.get(i).goods_num, ordermodel.extend_order_goods.get(i).goods_price));
            ids += ordermodel.extend_order_goods.get(i).goods_num + "|" + ordermodel.extend_order_goods.get(i).goods_price + ",";
        }
        totalPrice_tv.setText("¥" + new DecimalFormat("##0.00").format(totalPrice));
    }


}
