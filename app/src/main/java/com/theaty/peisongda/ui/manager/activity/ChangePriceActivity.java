package com.theaty.peisongda.ui.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.GoodsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.ui.manager.adapter.ChangePriceAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import foundation.base.activity.BaseActivity;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;
import foundation.toast.ToastUtil;
import foundation.widget.swiperefresh.SuperSwipeRefreshLayout;

import static com.theaty.peisongda.notification.NotificationKey.notifiCaipu;

public class ChangePriceActivity extends BaseActivity implements NotificationListener {
    @BindView(R.id.manager_price_list)
    RecyclerView managerPriceList;
    @BindView(R.id.swipe_layout_manager_price)
    SuperSwipeRefreshLayout managerPriceListPrice;
    private int curpage = 1;
    ChangePriceAdapter listAdapter;
    private ArrayList<GoodsModel> goodsModels = new ArrayList<>();
    private ArrayList<GoodsModel> goodsModelsAll = new ArrayList<>();
    private String ids = "";

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_change_price);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBack();
        setTitle("修改价格");
        setRightTitle("完成");
        showLoading();
        initView();
        getdata(1);
        NotificationCenter.defaultCenter.addListener(notifiCaipu, this);
    }

    @Override
    protected void goNext() {
        super.goNext();
        ids = "";
        for (int i = 0; i < goodsModelsAll.size(); i++) {
            ids += goodsModelsAll.get(i).goods_id + "|" + goodsModelsAll.get(i).goods_price + ",";
        }
        if (ids.length() > 0) {
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            new MemberModel().goods_price_change(ids, new BaseModel.BaseModelIB() {
                @Override
                public void StartOp() {
                    showLoading();
                }

                @Override
                public void successful(Object o) {
                    hideLoading();
                    NotificationCenter.defaultCenter.postNotification(notifiCaipu);
                    finish();
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg());

                }
            });

        } else {
            ToastUtil.showToast("没有商品");
        }
    }

    void initView() {

        managerPriceListPrice.setDirection(SuperSwipeRefreshLayout.Direction.BOTTOM);
        managerPriceListPrice.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (SuperSwipeRefreshLayout.Direction.TOP == direction) {
//                    curpage = 1;
//                    getdata(curpage);
//                    listAdapter.notifyDataSetChanged();
                } else if (SuperSwipeRefreshLayout.Direction.BOTTOM == direction) {
//                    curpage++;
//                    getdata(curpage);
//                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // holder.traceRecycler.addItemDecoration(new SpacesItemDecoration(2));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        managerPriceList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new ChangePriceAdapter(this, goodsModels, 3);
        listAdapter.setOnItemClickLitener(new ChangePriceAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(ArrayList<GoodsModel> goodsModels) {
                goodsModelsAll = goodsModels;
            }
        });
        //  managerPriceList.setAdapter(listAdapter);

    }


    void getdata(final int curpage) {

        new MemberModel().dishes_manage(curpage, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                hideLoading();
                goodsModels = (ArrayList<GoodsModel>) o;
                if (goodsModels == null || goodsModels.size() == 0) {
                    if (managerPriceListPrice != null) {
                        managerPriceListPrice.setRefreshing(false);
                    }

                    if (curpage > 1) {
                        ToastUtil.showToast("没有更多数据了！");
                    }
                    goodsModels = new ArrayList<GoodsModel>();
                }
                if (null == listAdapter) {
                    listAdapter = new ChangePriceAdapter(ChangePriceActivity.this, goodsModels, 3);
                    managerPriceList.setAdapter(listAdapter);
                } else {
                    if (curpage == 1) {
                        // managerPriceList.setAdapter(listAdapter);

                        listAdapter.upDate(goodsModels);
                        managerPriceList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    } else {
                        listAdapter.addDate(goodsModels);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                if (managerPriceListPrice != null) {
                    managerPriceListPrice.setRefreshing(false);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading();
                if (managerPriceListPrice != null) {
                    managerPriceListPrice.setRefreshing(false);
                }
                ToastUtil.showToast(resultsModel.getErrorMsg());
            }
        });
    }


    public static void into(Context context) {
        Intent starter = new Intent(context, ChangePriceActivity.class);
        context.startActivity(starter);
    }

    @Override
    public boolean onNotification(Notification notification) {
        return false;
    }
}
