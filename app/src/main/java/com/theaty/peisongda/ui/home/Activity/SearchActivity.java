package com.theaty.peisongda.ui.home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.CateringModel;
import com.theaty.peisongda.ui.home.Adapter.HomeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseActivity;
import foundation.widget.swiperefresh.SuperSwipeRefreshLayout;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_view)
    LinearLayout searchView;
    @BindView(R.id.dating_list)
    RecyclerView datingList;
    @BindView(R.id.swipe_layout_search)
    SuperSwipeRefreshLayout swipeLayoutSearch;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.quxiao)
    TextView quxiao;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_search);
    }

    private int curpage = 1;
    HomeAdapter listAdapter;
    private ArrayList<CateringModel> cateringModels;
    private String searchKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                    String searchtext = content.getText().toString();
                    goSearch(searchtext);
                    return true;
                }
                return false;
            }
        });
        initView();
//        getdata(1);

    }

    private void goSearch(String searchtext) {
        searchKey = searchtext;
        getdata(1);
    }


    void initView() {

        swipeLayoutSearch.setDirection(SuperSwipeRefreshLayout.Direction.BOTH);
        swipeLayoutSearch.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // holder.traceRecycler.addItemDecoration(new SpacesItemDecoration(2));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        datingList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new HomeAdapter(this, cateringModels, 3);
        listAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(int position) {
//                getdata(1);
            }
        });
        datingList.setAdapter(listAdapter);

    }

    void getdata(final int curpage) {

//        new MemberModel().index(searchKey, curpage, new BaseModel.BaseModelIB() {
//            @Override
//            public void StartOp() {
//
//            }
//
//            @Override
//            public void successful(Object o) {
//
//                cateringModels = (ArrayList<CateringModel>) o;
//                if (cateringModels == null || cateringModels.size() == 0) {
//
//                    swipeLayoutSearch.setRefreshing(false);
//                    if (curpage > 1) {
//                        ToastUtil.showToast("没有更多数据了！");
//                    } else {
//                        ToastUtil.showToast("没有搜索到您想要的内容！");
//                    }
//                    cateringModels = new ArrayList<CateringModel>();
//                }
//                if (null == listAdapter) {
//                    listAdapter = new HomeAdapter(SearchActivity.this, cateringModels, 3);
//                    datingList.setAdapter(listAdapter);
//                } else {
//                    if (curpage == 1) {
//                        // datingList.setAdapter(listAdapter);
//                        listAdapter.upDate(cateringModels);
//                        datingList.setAdapter(listAdapter);
//                        listAdapter.notifyDataSetChanged();
//                    } else {
//                        listAdapter.addDate(cateringModels);
//                        //datingList.setAdapter(listAdapter);
//                        // listAdapter.notifyDataSetChanged();
//
//                    }
//                }
//                swipeLayoutSearch.setRefreshing(false);
//            }
//
//            @Override
//            public void failed(ResultsModel resultsModel) {
//
//                swipeLayoutSearch.setRefreshing(false);
//                ToastUtil.showToast(resultsModel.getErrorMsg());
//            }
//
//
//        });
    }

    public static void into(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);

        context.startActivity(starter);
    }





    @OnClick({R.id.search, R.id.quxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                String searchtext = content.getText().toString();
                goSearch(searchtext);
                break;
            case R.id.quxiao:
                finish();
                break;
        }
    }
}
