package com.theaty.peisongda.ui.home.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.GoodsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lixiangyi on 2017/12/11.
 */

public class OrderItemAdapter extends RecyclerView.Adapter {
    @BindView(R.id.name_item_tv)
    TextView nameItemTv;
    @BindView(R.id.sum_item_tv)
    TextView sumItemTv;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private ArrayList<GoodsModel> dataList;

    //构造函数
    public OrderItemAdapter(ArrayList<GoodsModel> mData) {

        this.dataList = mData;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder

        //对于Body中的item，我们也返回所对应的ViewHolder
        return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_goods_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        //其他条目中的逻辑在此
        final BodyViewHolder holder1 = (BodyViewHolder) viewHolder;
//
        holder1.nameItemTv.setText(dataList.get(position).goods_name);
        if (dataList.get(position).dishes_unit!=null) {
            holder1.sumItemTv.setText(dataList.get(position).goods_num+dataList.get(position).dishes_unit);
        }else {
            holder1.sumItemTv.setText(dataList.get(position).goods_num+"斤");
        }

        if (mOnItemClickLitener != null) {
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v.getRootView(), position);
                }
            });
        }
    }


    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (dataList!=null) {
            return dataList.size();
        }else {
            return 0;
        }
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */


    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_item_tv)
        TextView nameItemTv;
        @BindView(R.id.sum_item_tv)
        TextView sumItemTv;

        public BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


}

