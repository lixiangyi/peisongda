package com.theaty.peisongda.ui.home.Adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class IRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> infos;

    private void initInfos() {
        infos = infos == null ? new ArrayList<T>() : infos;
    }

    public void addInfo(T info) {
        initInfos();
        this.infos.add(info);
    }

    public void addInfo(int index, T info) {
        initInfos();
        this.infos.add(index, info);
        notifyItemInserted(index);
        notifyItemRangeChanged(index, getItemCount());
    }

    public void removeInfo(T info) {
        initInfos();
        this.infos.remove(info);
    }

    public void removeIndex(int index) {
        initInfos();
        this.infos.remove(index);
    }

    public void removeIndexAnim(int index) {
        initInfos();
        this.infos.remove(index);
        notifyItemRemoved(index);
//        if (getCount() <= 0) {
//            if (null != onDeleteListener) {
//                onDeleteListener.onDelete();
//            }
//        }
    }

    public void removeIndexAnim2(int index) {
        initInfos();
        this.infos.remove(index);
        notifyItemRemoved(index);
        if (index != getItemCount())
            notifyItemRangeChanged(index, getItemCount() - index);
//        if (getCount() <= 0) {
//            if (null != onDeleteListener) {
//                onDeleteListener.onDelete();
//            }
//        }
    }

    public int getIndex(int position) {
        return position;
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.infos.set(getIndex(position), item);
            notifyItemChanged(position);
        }
    }

    public void addInfos(List<T> infos) {
        initInfos();
        this.infos.addAll(infos);
    }

    public void addInfos(int index, List<T> infos) {
        initInfos();
        this.infos.addAll(index, infos);
    }

    public void setDatas(List<T> infos) {
        initInfos();
        this.infos.clear();
        this.infos.addAll(infos);
    }


    public void removeInfos(List<T> infos) {
        initInfos();
        this.infos.removeAll(infos);
    }


    public void clear() {
        initInfos();
        this.infos.clear();
    }

    public List<T> getInfos() {
        initInfos();
        return infos;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public T getItem(int position) {
        return this.infos.get(position);
    }

    public int getCount() {
        return infos == null ? 0 : this.infos.size();
    }

    public interface OnDeleteListener {
        void onDelete();
    }

    public OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

}
