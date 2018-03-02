package com.theaty.peisongda.ui.home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.GoodsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lixiangyi on 2017/7/18.
 */

public class SumAdapter extends RecyclerView.Adapter {


    private boolean isDeleteAble;

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
    private ArrayList<GoodsModel> mDatas;
    private int type;

    public SumAdapter(Context context, ArrayList<GoodsModel> Models) {

        this.context = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
        if (null != Models) {
            this.mDatas = Models;
        }
        // notifyDataSetChanged();
    }


    public void upDate(ArrayList<GoodsModel> Models) {

        if (null != Models) {
            this.mDatas = Models;
        }

    }

    public void addDate(ArrayList<GoodsModel> Models) {

        if (null != Models) {
            this.mDatas.addAll(Models);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (VIEW_TYPE == viewType) {
            View view = mInflater.inflate(R.layout.empty_layout, parent, false);
            viewHolder = new MyEmptyHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.sum_layout_item,
                    parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {

        if (getItemViewType(position) == VIEW_TYPE) {
            final MyEmptyHolder holder = (MyEmptyHolder) holder1;
            holder.tv_empty.setText("没有数据了");


        } else {
            final ViewHolder holder = (ViewHolder) holder1;


            holder.vNameTv.setText(mDatas.get(position).goods_name);
            holder.numTv.setText(mDatas.get(position).goods_num+"斤左右");


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
        return mDatas == null || mDatas.size() == 0 ? 1 : mDatas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_name_tv)
        TextView vNameTv;
        @BindView(R.id.num_tv)
        TextView numTv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
