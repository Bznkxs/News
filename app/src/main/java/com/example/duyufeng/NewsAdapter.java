package com.example.duyufeng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinkedList<NewsItem> data;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt_main;
        public TextView txt_affl;
        public TextView txt_no;
        public Button dotButton;
        public Button barButton;
        public com.example.duyufeng.NewsItemLayout view;
        public MyViewHolder(View v) {
            super(v);
            txt_main = v.findViewById(R.id.name);
            txt_affl = v.findViewById(R.id.author);
            txt_no   = v.findViewById(R.id.no);
            dotButton = v.findViewById(R.id.dots_button);
            barButton = v.findViewById(R.id.bar_button);
            view     = (com.example.duyufeng.NewsItemLayout)v;
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    public NewsAdapter(LinkedList<NewsItem> myDataset) {
        data = myDataset;
    }

    public NewsAdapter() {
        data = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {


        // 通过判断显示类型，来创建不同的View
        if (viewType == TYPE_ITEM) {
            // create a new view
            View convertView = new com.example.duyufeng.NewsItemLayout(parent.getContext());
            //View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_without_icon,parent,false);
            MyViewHolder holder = new MyViewHolder(convertView);
            return holder;

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder holder1 = (MyViewHolder)holder;
            holder1.txt_main.setText(data.get(position).getName());
            holder1.txt_affl.setText(data.get(position).getAuthor());
            holder1.txt_no.setText(Integer.toString(position + 1));
            holder1.view.item = data.get(position);
        }
        else {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }


}