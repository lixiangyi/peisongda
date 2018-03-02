package com.theaty.peisongda.ui.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.GoodsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.ui.manager.activity.ChangePriceActivity;
import com.theaty.peisongda.ui.manager.activity.SucaiSelectActivity;
import com.theaty.peisongda.ui.manager.adapter.ManagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import foundation.base.fragment.BaseFragment;
import foundation.notification.NotificationCenter;
import foundation.notification.NotificationListener;
import foundation.toast.ToastUtil;
import foundation.widget.swiperefresh.SuperSwipeRefreshLayout;

import static com.theaty.peisongda.notification.NotificationKey.notifiCaipu;

/**
 * Created by yecal on 2017/12/11.
 */

public class ManagerFragment extends BaseFragment implements NotificationListener {

    @BindView(R.id.manager_list)
    RecyclerView managerList;
    @BindView(R.id.swipe_layout_manager)
    SuperSwipeRefreshLayout swipeLayoutManager;
    Unbinder unbinder;
    @BindView(R.id.bianji)
    TextView bianji;
    @BindView(R.id.xinzeng)
    TextView xinzeng;
    private int curpage = 1;
    ManagerAdapter listAdapter;
    private ArrayList<GoodsModel> goodsModels = new ArrayList<>();

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.fragment_circle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initView();
        getdata(1);
        NotificationCenter.defaultCenter.addListener(notifiCaipu, this);
    }

    void initView() {

        swipeLayoutManager.setDirection(SuperSwipeRefreshLayout.Direction.TOP);
        swipeLayoutManager.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (SuperSwipeRefreshLayout.Direction.TOP == direction) {
                    curpage = 1;
                    getdata(curpage);
                    listAdapter.notifyDataSetChanged();
                } else if (SuperSwipeRefreshLayout.Direction.BOTTOM == direction) {
//                    curpage++;
//                    getdata(curpage);
//                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        // holder.traceRecycler.addItemDecoration(new SpacesItemDecoration(2));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        managerList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new ManagerAdapter(getActivity(), goodsModels, 3);
        listAdapter.setOnItemClickLitener(new ManagerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(int position) {
                getdata(1);
            }
        });
        //  managerList.setAdapter(listAdapter);

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
                    if (swipeLayoutManager != null) {
                        swipeLayoutManager.setRefreshing(false);
                    }

                    if (curpage > 1) {
                        ToastUtil.showToast("没有更多数据了！");
                    }
                    goodsModels = new ArrayList<GoodsModel>();
                }
                if (null == listAdapter) {
                    listAdapter = new ManagerAdapter(getActivity(), goodsModels, 3);
                    managerList.setAdapter(listAdapter);
                } else {
                    if (curpage == 1) {
                        // managerList.setAdapter(listAdapter);

                        listAdapter.upDate(goodsModels);
                        managerList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    } else {
                        listAdapter.addDate(goodsModels);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                if (swipeLayoutManager != null) {
                    swipeLayoutManager.setRefreshing(false);
                }
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading();
                if (swipeLayoutManager != null) {
                    swipeLayoutManager.setRefreshing(false);
                }
                ToastUtil.showToast(resultsModel.getErrorMsg());
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

        NotificationCenter.defaultCenter.removeListener(notifiCaipu, this);
        unbinder.unbind();
    }

    @Override
    public boolean onNotification(Notification notification) {
        if (notification.key.equals(notifiCaipu)) {
            getdata(1);
            return true;
        }
        return false;
    }

    @OnClick({R.id.bianji, R.id.xinzeng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bianji:
                ChangePriceActivity.into(getActivity());
                break;
            case R.id.xinzeng:
                SucaiSelectActivity.into(getContext());
                break;
        }
    }
}
