package com.theaty.peisongda.ui.home.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.GoodsModel;
import com.theaty.peisongda.ui.home.Adapter.SumAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import foundation.base.activity.BaseActivity;

public class SumActivity extends BaseActivity {

    ArrayList<GoodsModel> goodsmodel;
    @BindView(R.id.sum_list)
    RecyclerView sumList;
    private SumAdapter listAdapter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_sum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("统计");
        registerBack();
        goodsmodel = (ArrayList<GoodsModel>) getIntent().getSerializableExtra("goods");
        initView();

    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // holder.traceRecycler.addItemDecoration(new SpacesItemDecoration(2));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sumList.setLayoutManager(linearLayoutManager);

        //设置适配器
        listAdapter = new SumAdapter(this, goodsmodel);
        listAdapter.setOnItemClickLitener(new SumAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onChange(int position) {
            }
        });
        sumList.setAdapter(listAdapter);
    }

    public static void start(Context context, ArrayList<GoodsModel> goods) {
        Intent starter = new Intent(context, SumActivity.class);
        starter.putExtra("goods", goods);
        context.startActivity(starter);
    }
}
