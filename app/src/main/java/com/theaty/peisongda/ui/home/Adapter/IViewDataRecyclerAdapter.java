package com.theaty.peisongda.ui.home.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class IViewDataRecyclerAdapter<T, E extends ViewDataBinding> extends IRecyclerViewAdapter<T,
        IViewDataRecyclerAdapter.ViewHolder> {

    private View rootView;
    private Context mContext;
    private LayoutInflater mInflater;

    public View getRootView() {
        return rootView;
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public float width = -1;

    public void setItemWith(float width) {
        this.width = width;
    }

    @Override
    public IViewDataRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (rootView == null) {
            rootView = parent;
            mContext = rootView.getContext();
            mInflater = LayoutInflater.from(mContext);
        }
        ViewDataBinding view = DataBindingUtil.inflate(mInflater, getItemLayoutId(viewType), parent,
                false);
        if (width > -1)
            view.getRoot().getLayoutParams().width = (int) width;
        ViewHolder viewHolder = new ViewHolder(view.getRoot());
        viewHolder.setBinding(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IViewDataRecyclerAdapter.ViewHolder holder, int position) {
        bindData((E) holder.getBinding(), getItem(position), position);
        (holder.getBinding()).executePendingBindings();
    }

    protected abstract int getItemLayoutId(int viewType);

    protected abstract void bindData(E e, T info, int position);


    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ViewDataBinding e;

        public void setBinding(ViewDataBinding e) {
            this.e = e;
        }

        public ViewDataBinding getBinding() {
            return e;
        }

    }


}
