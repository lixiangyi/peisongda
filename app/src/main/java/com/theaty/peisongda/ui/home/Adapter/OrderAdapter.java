package com.theaty.peisongda.ui.home.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.OrderModel;
import com.theaty.peisongda.ui.home.Activity.OrderDetialActivity;

import java.util.ArrayList;


/**
 * Created by lixiangyi on 2017/7/18.
 */

public class OrderAdapter extends RecyclerView.Adapter {
    public interface OnItemClickLitener {
        void onItemClick(View view, OrderModel orderModel);

        void onChange(ArrayList<OrderModel> Models);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<OrderModel> mDatas;
    private OrderItemAdapter sucaiItemAdapter;
    private int type;

    public OrderAdapter(Context context, ArrayList<OrderModel> Models) {

        this.context = context;

        mInflater = LayoutInflater.from(context);
        if (null != Models) {
            this.mDatas = Models;
        }
        // notifyDataSetChanged();
    }


    public void upDate(ArrayList<OrderModel> Models) {

        if (null != Models) {
            this.mDatas = Models;
        }
        if (mOnItemClickLitener != null) {
            mOnItemClickLitener.onChange(mDatas);
        }

    }

    public void addDate(ArrayList<OrderModel> Models) {

        if (null != Models) {
            this.mDatas.addAll(Models);
        }
        if (mOnItemClickLitener != null) {
            mOnItemClickLitener.onChange(mDatas);
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
            View view = mInflater.inflate(R.layout.tabs_layout_item, parent,
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

            sucaiItemAdapter = new OrderItemAdapter(mDatas.get(position).extend_order_goods);

            // 实例化一个GridLayoutManager，列数为3
            final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            //把我们自定义的ItemDecoration设置给RecyclerView
//           hotSearchList.addItemDecoration(new MyItemDecoration());
            //把LayoutManager设置给RecyclerView
            holder.r.setLayoutManager(layoutManager);
            holder.r.setAdapter(sucaiItemAdapter);
            sucaiItemAdapter.setOnItemClickLitener(new OrderItemAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position1) {
                    OrderDetialActivity.into(context,mDatas.get(position));
                }
            });
            holder.t.setText(mDatas.get(position).reciver_info.reciver_name);
            holder.t_t.setText(mDatas.get(position).add_time);
            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.print_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(holder.print_btn, mDatas.get(position));
                    }
                });

            }

            holder.do_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetialActivity.into(context,mDatas.get(position));
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetialActivity.into(context,mDatas.get(position));
                }
            });


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
        TextView t;
        TextView t_t;
        Button print_btn;
        Button do_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            r = (RecyclerView) itemView.findViewById(R.id.waiters_table_list);
            t = (TextView) itemView.findViewById(R.id.order_name);
            t_t = (TextView) itemView.findViewById(R.id.order_time);
            print_btn = (Button) itemView.findViewById(R.id.print_btn);
            do_btn = (Button) itemView.findViewById(R.id.do_order);
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
