package com.theaty.peisongda.ui.manager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.GoodsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.ui.home.utils.CashierInputFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import foundation.toast.ToastUtil;

/**
 * Created by lixiangyi on 2017/7/18.
 */

public class ManagerAdapter extends RecyclerView.Adapter {


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

    public ManagerAdapter(Context context, ArrayList<GoodsModel> Models, int type) {

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
            View view = mInflater.inflate(R.layout.manager_layout_item1,
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
            if (mDatas.get(position).dishes_unit!=null) {
                holder.priceTime.setText("¥"+new DecimalFormat("##0.00").format(mDatas.get(position).goods_price)+"/"+mDatas.get(position).dishes_unit);
            }else {
                holder.priceTime.setText("¥"+new DecimalFormat("##0.00").format(mDatas.get(position).goods_price)+"/斤");
            }
            holder.priceTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText et = new EditText(v.getContext());
                    et.setFocusable(true);
                    et.setFocusableInTouchMode(true);
                    et.requestFocus();
                    et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et.setHint(new DecimalFormat("##0.00").format(mDatas.get(position).goods_price));
//                    et.setSelection(new DecimalFormat("##0.00").format(mDatas.get(position).goods_price).length());//将光标移至文字末尾
                    et.setRawInputType(Configuration.KEYBOARD_QWERTY);
                    InputFilter[] filters = {new CashierInputFilter(2)};
                    et.setFilters(filters);
                    AlertDialog.Builder dialog =   new AlertDialog.Builder(context)
                           .setTitle("请填写"+"《"+mDatas.get(position).goods_name+"》"+"要修改的价格")
                           .setView(et)
                           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   final String price = et.getText().toString();
                                   if (price.length()>0) {
                                       new MemberModel().edit_price(price, mDatas.get(position).goods_id, new BaseModel.BaseModelIB() {
                                           @Override
                                           public void StartOp() {

                                           }

                                           @Override
                                           public void successful(Object o) {
//                                           holder.priceTime.setText("¥"+price+".00/斤");
                                               mDatas.get(position).goods_price =Double.parseDouble(price) ;
                                               holder.priceTime.setText("¥"+new DecimalFormat("##0.00").format(mDatas.get(position).goods_price)+"/斤");
                                               ToastUtil.showToast(o.toString());
//                                               notifyItemChanged(position);
                                           }

                                           @Override
                                           public void failed(ResultsModel resultsModel) {
                                               ToastUtil.showToast(resultsModel.getErrorMsg());

                                           }
                                       });
                                   }else {
//                                       ToastUtil.showToast("请输入价格");
                                   }


                               }
                           })
                           .setNegativeButton("取消",null);
                    //下面是弹出键盘的关键处
                    AlertDialog tempDialog = dialog.create();
                    tempDialog.setView(et, 0, 0, 0, 0);

                    tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        public void onShow(DialogInterface dialog) {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });

                    tempDialog.show();


                }
            });
            holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("提示")
                            .setMessage("确定要删除 "+ mDatas.get(position).goods_name+" 吗？ ")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new MemberModel().delete_goods(mDatas.get(position).goods_id, new BaseModel.BaseModelIB() {
                                        @Override
                                        public void StartOp() {

                                        }

                                        @Override
                                        public void successful(Object o) {
                                            mDatas.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, getItemCount());
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(370);//休息
//                                                isDeleteAble = true;//可点击按钮
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }

                                        @Override
                                        public void failed(ResultsModel resultsModel) {
                                            ToastUtil.showToast(resultsModel.getErrorMsg());
                                        }
                                    });



                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();


                }
            });

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
        @BindView(R.id.price_time)
        TextView priceTime;
        @BindView(R.id.delete_icon)
        ImageView deleteIcon;

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
