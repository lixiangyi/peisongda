package com.theaty.peisongda.ui.manager.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.DishesClassModel;

import java.util.ArrayList;


/**
 * Created by lixiangyi on 2017/7/18.
 */

public class SucaiClassAdapter extends RecyclerView.Adapter {
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onChange(int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<DishesClassModel> mDatas;
    private SucaiItemAdapter sucaiItemAdapter;
    private int type;

    public SucaiClassAdapter(Context context, ArrayList<DishesClassModel> Models) {

        this.context = context;

        mInflater = LayoutInflater.from(context);
        if (null != Models) {
            this.mDatas = Models;
        }
        // notifyDataSetChanged();
    }


    public void upDate(ArrayList<DishesClassModel> Models) {

        if (null != Models) {
            this.mDatas = Models;
        }

    }

    public void addDate(ArrayList<DishesClassModel> Models) {

        if (null != Models) {
            this.mDatas.addAll(Models);
        }
          notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (VIEW_TYPE == viewType) {
            View view = mInflater.inflate(R.layout.empty_layout, parent, false);
            viewHolder = new MyEmptyHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.tabs_layout_item_cai, parent,
                    false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {

        if (getItemViewType(position) == VIEW_TYPE) {
            final MyEmptyHolder holder = (MyEmptyHolder) holder1;
            //Glide.with(context).load(R.drawable.youkule).into(holder.Iv_Image);



        } else {
            final ViewHolder holder = (ViewHolder) holder1;
            sucaiItemAdapter = new SucaiItemAdapter(mDatas.get(position));
            sucaiItemAdapter.setOnItemClickLitener(new SucaiItemAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    mOnItemClickLitener.onChange( position);
                }
            });
            // 实例化一个GridLayoutManager，列数为3
            final GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
            //把我们自定义的ItemDecoration设置给RecyclerView
//        hotSearchList.addItemDecoration(new MyItemDecoration());

            //调用以下方法让RecyclerView的第一个条目仅为1列
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
                    //而如果不是0，则说明不是Header，就占用1列即可
                    return sucaiItemAdapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
                }
            });

            //把LayoutManager设置给RecyclerView
            holder.r.setLayoutManager(layoutManager);
            holder.r.setAdapter(sucaiItemAdapter);



            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(holder.itemView, position);
                    }
                });

            }
        }
    }

    private static final int VIEW_TYPE = -1;

    /**
     * 获取条目 View填充的类型
     * 默认返回0
     * 将lists为空返回-1
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position) {

        if (mDatas == null || mDatas.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        int x = mDatas == null ? 1 : mDatas.size();
        return mDatas.size() == 0 ? 1 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView r;


        public ViewHolder(View itemView) {
            super(itemView);
            r = (RecyclerView) itemView.findViewById(R.id.waiters_table_list);

        }


    }


    public class MyEmptyHolder extends RecyclerView.ViewHolder {
        TextView tv_empty;
        ImageView Iv_Image;

        public MyEmptyHolder(View itemView) {
            super(itemView);

            tv_empty = (TextView) itemView.findViewById(R.id.empty_tv);
            Iv_Image = (ImageView) itemView.findViewById(R.id.empty_iv);
        }
    }





}
