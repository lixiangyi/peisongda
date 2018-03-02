package com.theaty.peisongda.ui.home.Adapter;

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
import foundation.base.activity.BaseActivity;


/**
 * Created by lixiangyi on 2017/7/18.
 */

public class OrderDetialAdapter extends RecyclerView.Adapter {




    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onChange(double d);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private BaseActivity context;
    private LayoutInflater mInflater;
    private ArrayList<GoodsModel> mDatas;
    private OrderItemAdapter sucaiItemAdapter;
    private int type;

    public OrderDetialAdapter(Context context, ArrayList<GoodsModel> Models) {

        this.context = (BaseActivity) context;

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

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (VIEW_TYPE == viewType) {
            View view = mInflater.inflate(R.layout.empty_layout, parent, false);
            viewHolder = new MyEmptyHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.detial_goods_item, parent,
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
//            holder.t.setText(mDatas.get(position).reciver_info.reciver_name);
            Glide.with(context).load(mDatas.get(position).goods_image).into(holder.goodsImage);
            holder.goodsName.setText(mDatas.get(position).goods_name);
            if (mDatas.get(position).dishes_unit!=null) {
                holder.num_unit.setText("数量/"+mDatas.get(position).dishes_unit);
            }else {
                holder.num_unit.setText("数量/斤");
            }

//            holder.priceItem.setText("¥" +new DecimalFormat("##0.00").format(ArithUtil.mul(mDatas.get(position).goods_num , mDatas.get(position).goods_price)) + "");

            if ( holder.numChange.getEditText().getTag() instanceof TextWatcher) {
                holder.numChange.getEditText().removeTextChangedListener((TextWatcher)  holder.numChange.getEditText().getTag());
            }

            holder.numChange.setPointLength(1);
            holder.numChange.setUpdateStepValue(0.1);
            holder.numChange.getEditText().setTextColor(context.getResources().getColor( R.color.color_666666) );
            holder.numChange.setContent(mDatas.get(position).goods_num);
            TextWatcher numTextWatcher =  new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

//                            holder.sum.setText(s);
                    if (s != null && !TextUtils.isEmpty(s)) {
                        if (s.length() > 0) {
                            mDatas.get(position).goods_num = Double.parseDouble(s + "");
//                            holder.priceItem.setText("¥" + new DecimalFormat("##0.00").format(ArithUtil.mul(mDatas.get(position).goods_num, mDatas.get(position).goods_price)) + "");
                            if (mOnItemClickLitener != null) {
                                mOnItemClickLitener.onChange(mDatas.get(position).goods_num);
                            }
                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            holder.numChange.getEditText().addTextChangedListener(numTextWatcher);
            holder.numChange.getEditText().setTag(numTextWatcher);
            //单价修改
            if ( holder.priceChange.getEditText().getTag() instanceof TextWatcher) {
                holder.priceChange.getEditText().removeTextChangedListener((TextWatcher)  holder.priceChange.getEditText().getTag());
            }
            Glide.with(context).load(R.drawable.add_green).into(  holder.priceChange.getRightImageView());
            Glide.with(context).load(R.drawable.reduce_green).into(  holder.priceChange.getLeftImageView());
            holder.priceChange.setPointLength(2);
            holder.priceChange.setUpdateStepValue(0.1);
            holder.priceChange.getEditText().setTextColor(context.getResources().getColor( R.color.color_666666) );
            holder.priceChange.setContent(mDatas.get(position).goods_price);
            TextWatcher PriceTextWatcher=  new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

//                            holder.sum.setText(s);
                    if (s != null && !TextUtils.isEmpty(s)) {
                        if (s.length() > 0) {
                            mDatas.get(position).goods_price = Double.parseDouble(s + "");
//                            holder.priceItem.setText("¥" + new DecimalFormat("##0.00").format(ArithUtil.mul(mDatas.get(position).goods_num, mDatas.get(position).goods_price)) + "");
                            if (mOnItemClickLitener != null) {
                                mOnItemClickLitener.onChange(mDatas.get(position).goods_num);
                            }
                        }

                    }
//                    holder.sum.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
//                            holder.sum.setSelection(s.length());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            holder.priceChange.getEditText().addTextChangedListener(PriceTextWatcher);
            holder.priceChange.getEditText().setTag(PriceTextWatcher);
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

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.num_unit)
        TextView num_unit;
        @BindView(R.id.num_change)
        EditNumberView numChange;
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
