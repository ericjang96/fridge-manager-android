package com.example.kevin.fridgemanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on Aug 30, 2018
 **/
// Source code from https://gist.github.com/Plumillon/f85c6be94e2fdaf339b9
public abstract class RecyclerViewAdapter<RecyclerViewItem> extends RecyclerView.Adapter<RecyclerViewAdapter.GenericViewHolder> {
    private List<RecyclerViewItem> items;
    private Context context;
    private OnViewHolderClick<RecyclerViewItem> listener;

    public interface OnViewHolderClick<RecyclerViewItem> {
        void onClick(View view, int position, RecyclerViewItem item);
    }

    public class GenericViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Map<Integer, View> views;
        private TextWatcher watcher;

        public GenericViewHolder(View view, OnViewHolderClick listener) {
            super(view);
            views = new HashMap<>();
            views.put(0, view);

            if (listener != null)
                view.setOnClickListener(this);
        }

        public GenericViewHolder(View view){
            super(view);
            views = new HashMap<>();
            views.put(0, view);
        }

        @Override
        public void onClick(View view) {
            if (listener != null)
                listener.onClick(view, getAdapterPosition(), getItem(getAdapterPosition()));
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                views.put(id, view);
        }

        public View initViewAndGetView(int id){
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                views.put(id, view);

            return view;
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (views.containsKey(id))
                return views.get(id);
            else
                initViewById(id);

            return views.get(id);
        }

        public TextView getTextView(int id){
            if (views.containsKey(id))
                return (TextView) views.get(id);
            else
                initViewById(id);

            return (TextView) views.get(id);
        }

        public void setTextWatcher(TextWatcher watcher){
            this.watcher = watcher;
        }

        public TextWatcher getTextWatcher(){
            return watcher;
        }
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(RecyclerViewItem item, RecyclerViewAdapter.GenericViewHolder viewHolder);

    protected abstract void bindViewPayloads(RecyclerViewItem item, RecyclerViewAdapter.GenericViewHolder viewHolder, @NonNull List<Object> payloads);

    public RecyclerViewAdapter(Context context) {
        this(context, null);
    }

    public RecyclerViewAdapter(Context context, OnViewHolderClick<RecyclerViewItem> listener) {
        super();
        this.context = context;
        this.listener = listener;
        items = new ArrayList<>();
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new GenericViewHolder(createView(context, viewGroup, viewType), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.GenericViewHolder holder, int position) {
        bindView(getItem(position), holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.GenericViewHolder holder, int position, @NonNull List<Object> payloads) {
        bindViewPayloads(getItem(position), holder, payloads);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public RecyclerViewItem getItem(int index) {
        return ((items != null && index < items.size()) ? items.get(index) : null);
    }

    public Context getContext() {
        return context;
    }

    public void setList(List<RecyclerViewItem> list) {
        items = list;
    }

    public List<RecyclerViewItem> getList() {
        return items;
    }

    public void setClickListener(OnViewHolderClick listener) {
        this.listener = listener;
    }

    public void addAll(List<RecyclerViewItem> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void reset() {
        items.clear();
        notifyDataSetChanged();
    }

    public void removeAt(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getList().size());
    }

    public void insertAtPosition(RecyclerViewItem item, int position){
        items.add(position, item);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getList().size());
    }

    public void insert(RecyclerViewItem item){
        int position = items.size() - 1;
        items.add(item);
        notifyItemRangeChanged(position, items.size());
    }
}
