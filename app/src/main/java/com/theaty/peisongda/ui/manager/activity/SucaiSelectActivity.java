package com.theaty.peisongda.ui.manager.activity;

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
import com.theaty.peisongda.model.peisongda.DishesClassModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.ui.manager.adapter.SucaiClassAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseActivity;
import foundation.notification.NotificationCenter;
import foundation.toast.ToastUtil;
import foundation.widget.swiperefresh.SuperSwipeRefreshLayout;

import static com.theaty.peisongda.notification.NotificationKey.notifiCaipu;

public class SucaiSelectActivity extends BaseActivity {
    private static final String ORDERID = "orderid";
    private static final String OLDTABLE = "oldTable";
    @BindView(R.id.waiter_table_list)
    RecyclerView waiterTableList;
    @BindView(R.id.swipe_layout_tablelist)
    SuperSwipeRefreshLayout swipeLayoutTablelist;
    @BindView(R.id.nest_btn)
    TextView nestBtn;

    private SucaiClassAdapter listAdapter;
    ArrayList<DishesClassModel> DishesClassModels = new ArrayList<>();
    ArrayList<ArrayList<String>> hotkeyss = new ArrayList<>();
    private String ids;
    private int orderid, oldtableid;
    private int curpage = 1;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_waiter_select);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderid = getIntent().getIntExtra(ORDERID, -1);
        oldtableid = getIntent().getIntExtra(OLDTABLE, -1);
        registerBack();
        setTitle("新增");
        setRightTitle("完成");
        showLoading();
        initData();
        initView();
    }

    private void initView() {
        swipeLayoutTablelist.setDirection(SuperSwipeRefreshLayout.Direction.TOP);
        swipeLayoutTablelist.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (SuperSwipeRefreshLayout.Direction.TOP == direction) {
                    curpage = 1;
                    initData();

                    listAdapter.notifyDataSetChanged();
                } else if (SuperSwipeRefreshLayout.Direction.BOTTOM == direction) {
                    curpage++;
                    initData();
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
//        for (int k = 0; k < 4; k++) {
//            ArrayList<String> hotkeys = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//
//                hotkeys.add1("桌" + i);
//            }
//            hotkeyss.add1(hotkeys);
//        }
        //实例化Adapter并且给RecyclerView设上

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // holder.traceRecycler.addItemDecoration(new SpacesItemDecoration(2));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        waiterTableList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new SucaiClassAdapter(this, DishesClassModels);
        listAdapter.setOnItemClickLitener(new SucaiClassAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(int position) {
//               getIds();

            }
        });
        waiterTableList.setAdapter(listAdapter);

    }

    private String getIds() {
        String idss = "";
        for (int i = 0; i < DishesClassModels.size(); i++) {
            for (int j = 0; j < DishesClassModels.get(i).child_dishes.size(); j++) {
                if (DishesClassModels.get(i).child_dishes.get(j).choose == 1) {
                    idss += DishesClassModels.get(i).child_dishes.get(j).dishes_id + ",";
                }
            }
        }
        if (idss.endsWith(",")) {
            idss = idss.substring(0, idss.length() - 1);
        }
        ids = idss;
        return ids;
    }

    private void initData() {
        new MemberModel().dishes_library(curpage,new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                DishesClassModels = (ArrayList<DishesClassModel>) o;

                if (DishesClassModels == null) {
                    // ToastUtil.showToast("您还没有签订的保险");
                    swipeLayoutTablelist.setRefreshing(false);
                    return;
                }
                if (null == listAdapter) {
                    listAdapter = new SucaiClassAdapter(SucaiSelectActivity.this, DishesClassModels);
                    waiterTableList.setAdapter(listAdapter);
                } else {
                    if (curpage == 1) {

                    listAdapter.upDate(DishesClassModels);
                    waiterTableList.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                    } else {
                        listAdapter.addDate(DishesClassModels);
                    //myFriendList.setAdapter(listAdapter);
                    // listAdapter.notifyDataSetChanged();

                    }
                }
                swipeLayoutTablelist.setRefreshing(false);
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg());
                swipeLayoutTablelist.setRefreshing(false);
            }
        });
    }

    public static void into(Context context) {
        Intent starter = new Intent(context, SucaiSelectActivity.class);
        context.startActivity(starter);
    }


    public static void into(Context context, int orderId, int oldTableId) {
        Intent starter = new Intent(context, SucaiSelectActivity.class);
        starter.putExtra(ORDERID, orderId);
        starter.putExtra(OLDTABLE, oldTableId);
        context.startActivity(starter);
    }

    @OnClick(R.id.nest_btn)
    public void onViewClicked() {
        go();
    }

    @Override
    protected void goNext() {
        super.goNext();
        go();
    }

    private void go() {
        getIds();
        if (ids.length()>0) {
            new MemberModel().save_dishes(ids, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    showLoading();
                }

                @Override
                public void successful(Object o) {
                    hideLoading(o.toString());
                    NotificationCenter.defaultCenter.postNotification(notifiCaipu);
                    finish();

                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg());
                }
            });
        }else {
            ToastUtil.showToast("请选择要添加的蔬菜");
        }

    }
}
