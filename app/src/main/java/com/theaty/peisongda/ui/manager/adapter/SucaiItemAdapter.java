package com.theaty.peisongda.ui.manager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.model.peisongda.DishesClassModel;
import com.theaty.peisongda.model.peisongda.DishesLibraryModel;

import java.util.ArrayList;

/**
 * Created by lixiangyi on 2017/12/11.
 */

public class SucaiItemAdapter extends RecyclerView.Adapter {
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private DishesClassModel dishesClassModel;
    private ArrayList<DishesLibraryModel> dataList;
    //构造函数
    public SucaiItemAdapter(DishesClassModel mData) {
        this.dishesClassModel = mData;
        this.dataList = mData.child_dishes;
    }

    /**
     * 判断当前position是否处于第一个
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_title_item, viewGroup,false));
        } else {
            //对于Body中的item，我们也返回所对应的ViewHolder
            return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiter_table_item, viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (isHeader(position)) {
//            //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改
//            ((HeaderViewHolder)viewHolder).getTextView().setText("This is the Header!!");
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.title.setText(dishesClassModel.class_name);
            if(dishesClassModel.isSelected) {
                holder.cb.setChecked(true);
            }else {
                holder.cb.setChecked(false);
            }
//            holder.cb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dishesClassModel.isSelected = ! dishesClassModel.isSelected;
//
//                    setAllcheck(dishesClassModel.isSelected);
//                }
//            });
//            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                }
//            });

        }else {
            //其他条目中的逻辑在此
            final BodyViewHolder holder1 = (BodyViewHolder) viewHolder;
//             if(DishesClassModel.isSelected){
//                 holder1.textView.setSelected(true);
//             }else {
//                 holder1.textView.setSelected(false);
//             }
            if (dataList.get(position-1).choose==1||dishesClassModel.isSelected) {
                holder1.textView.setSelected(true);
                holder1.imageViewS.setVisibility(View.VISIBLE);
            }else {
                holder1.textView.setSelected(false);
                holder1.imageViewS.setVisibility(View.GONE);
            }


            holder1.textView.setText(dataList.get(position-1).dishes_name);
            if (holder1.imageView.getTag(R.id.item_iv)==null||holder1.imageView.getTag(R.id.item_iv)!=dataList.get(position-1).dishes_image) {
                Glide.with(holder1.imageView.getContext())
                        .load(dataList.get(position - 1).dishes_image)
                        .dontAnimate()
                        .into(holder1.imageView);
                holder1.imageView.setTag(R.id.item_iv, dataList.get(position - 1).dishes_image);
            }else {

            }
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataList.get(position-1).choose==1) {
                        dataList.get(position-1).choose=0;
                        holder1.textView.setSelected(false);
                        holder1.imageViewS.setVisibility(View.GONE);
                    }else {
                        dataList.get(position-1).choose=1;
                        holder1.textView.setSelected(true);
                        holder1.imageViewS.setVisibility(View.VISIBLE);
                    }
//                    dataList.get(position-1).isSelected =  !dataList.get(position-1).isSelected;
//                    holder1.textView.setSelected(dataList.get(position-1).isSelected);
//                    compute(position);
                    mOnItemClickLitener.onItemClick(v,position);
                }
            });
            if (dataList.get(position-1).dishes_ischoose==1) {
                holder1.textView.setSelected(true);
                holder1.imageViewS.setVisibility(View.VISIBLE);
                holder1.imageViewNo.setVisibility(View.VISIBLE);
                holder1.itemView.setEnabled(false);
            }

        }
    }

    private void compute(int position) {
        int count = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).choose==1) {
                count++;
            }
        }
        if (count==dataList.size()){
            dishesClassModel.isSelected = true;
        }else {
            dishesClassModel.isSelected = false;
        }

       notifyItemChanged(position);
    }
    private void setAllcheck(boolean b) {
        for (int i = 0; i < dataList.size(); i++) {
            if (b) {
                dataList.get(i).choose = 1;
            }else {
                dataList.get(i).choose = 0;
            }

        }
        notifyDataSetChanged();
    }


    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果是0，就是头，否则则是其他的item
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
//        return ITEM_VIEW_TYPE_ITEM;
    }

    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox cb;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_static);
            cb = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ImageView imageViewS;
        public ImageView imageViewNo;
        public BodyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
            imageView = (ImageView) itemView.findViewById(R.id.item_iv);
            imageViewS = (ImageView) itemView.findViewById(R.id.item_iv_select);
            imageViewNo = (ImageView) itemView.findViewById(R.id.item_iv_no);
        }
        public TextView getTextView() {
            return textView;
        }
    }
}

