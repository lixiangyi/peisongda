package com.theaty.peisongda.ui.home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.CateringModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import foundation.date.DateUtils;

/**
 * Created by lixiangyi on 2017/7/18.
 */

public class HomeAdapter extends RecyclerView.Adapter {


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
    private ArrayList<CateringModel> mDatas;
    private int type;

    public HomeAdapter(Context context, ArrayList<CateringModel> Models, int type) {

        this.context = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
        if (null != Models) {
            this.mDatas = Models;
        }
        // notifyDataSetChanged();
    }



    public void upDate(ArrayList<CateringModel> Models) {

        if (null != Models) {
            this.mDatas = Models;
        }

    }

    public void addDate(ArrayList<CateringModel> Models) {

        if (null != Models) {
            this.mDatas.addAll(0,Models);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (VIEW_TYPE == viewType) {
            View view = mInflater.inflate(R.layout.empty_layout, parent, false);
            viewHolder = new MyEmptyHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.home_layout_item1,
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
            //Glide.with(context).load(R.drawable.youkule).into(holder.Iv_Image);
//            switch (type) {
//                case 1:
//                    holder.tv_empty.setText("您还没有粉丝，快去发帖找粉丝吧");
//                    break;
//                case 2:
//                    holder.tv_empty.setText("您还没有关注别人");
//                    break;
//                case 3:
//                    holder.tv_empty.setText("您还没有朋友，快去找朋友吧");
//                    break;
//                case 4:
//                    break;
//
//            }

        } else {
            final ViewHolder holder = (ViewHolder) holder1;
            Glide.with(context).load(mDatas.get(position).member_avatar)
                    .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
//             .transform(new GlideCircleTransform(context))
                    .into(holder.memberImg);
            holder.memberNameTv.setText(mDatas.get(position).member_nick);
            holder.addTime.setText(DateUtils.getWithDrawalistTime(mDatas.get(position).add_time*1000));
            holder.memberContentTv.setText(mDatas.get(position).catering_desc + "");
            /** 发布类型(1:求职,2:招聘,3:供应,4:求购)' , */
            switch (mDatas.get(position).class_id) {
                case 1:
                    Glide.with(context).load(R.drawable.qiuzhi_state).into(holder.stateIcon);
                    break;
                case 2:
                    Glide.with(context).load(R.drawable.zhaopin_state).into(holder.stateIcon);
                    break;
                case 3:
                    Glide.with(context).load(R.drawable.chushou_state).into(holder.stateIcon);
                    break;
                case 4:
                    Glide.with(context).load(R.drawable.qiugou_state).into(holder.stateIcon);
                    break;
            }

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
        return mDatas==null||mDatas.size() == 0 ? 1 : mDatas.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.member_img)
        ImageView memberImg;
        @BindView(R.id.member_name_tv)
        TextView memberNameTv;
        @BindView(R.id.state_icon)
        ImageView stateIcon;
        @BindView(R.id.member_content_tv)
        TextView memberContentTv;
        @BindView(R.id.add_time)
        TextView addTime;
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
