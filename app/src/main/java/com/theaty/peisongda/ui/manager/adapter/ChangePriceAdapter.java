package com.theaty.peisongda.ui.manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cwq.library.EditNumberView;
import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.GoodsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lixiangyi on 2017/7/18.
 */

public class ChangePriceAdapter extends RecyclerView.Adapter {


    private boolean isDeleteAble;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onChange(ArrayList<GoodsModel> goodsModels);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<GoodsModel> mDatas;
    private int type;

    public ChangePriceAdapter(Context context, ArrayList<GoodsModel> Models, int type) {

        this.context = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
        if (null != Models) {
            this.mDatas = Models;
            if (mOnItemClickLitener != null) {
                mOnItemClickLitener.onChange(mDatas);
            }
        }
        // notifyDataSetChanged();
    }


    public void upDate(ArrayList<GoodsModel> Models) {

        if (null != Models) {
            this.mDatas = Models;
            if (mOnItemClickLitener != null) {
                mOnItemClickLitener.onChange(mDatas);
            }
        }

    }

    public void addDate(ArrayList<GoodsModel> Models) {

        if (null != Models) {
            this.mDatas.addAll(Models);
            if (mOnItemClickLitener != null) {
                mOnItemClickLitener.onChange(mDatas);
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (VIEW_TYPE == viewType) {
            View view = mInflater.inflate(R.layout.empty_layout, parent, false);
            viewHolder = new MyEmptyHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.changeprice_layout_item,
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
            Glide.with(context).load(mDatas.get(position).goods_image).into(holder.image_goods);



            if ( holder.priceChange.getEditText().getTag() instanceof TextWatcher) {
                holder.priceChange.getEditText().removeTextChangedListener((TextWatcher)  holder.priceChange.getEditText().getTag());
            }

            holder.priceChange.setPointLength(2);
            holder.priceChange.setUpdateStepValue(0.1);
            holder.priceChange.setContent(mDatas.get(position).goods_price);

            TextWatcher textWatcher = new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

//                            holder.sum.setText(s);
                    if (s != null && !TextUtils.isEmpty(s)) {
                        if (s.length() > 0) {
                            mDatas.get(position).goods_price = Double.parseDouble(s + "");


                        }

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            holder.priceChange.getEditText().addTextChangedListener(textWatcher);
            holder.priceChange.getEditText().setTag(textWatcher);
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
        @BindView(R.id.image_goods)
        ImageView image_goods;
        @BindView(R.id.price_change)
        EditNumberView priceChange;

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
