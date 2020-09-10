package com.example.duyufeng;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinkedList<News> data, oData;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private int loadState = 2;
    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;

    public boolean showCacheButton = true;
    String filter = "";

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public final LinkedList<News> getData() {
        return data;
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
            pbLoading = itemView.findViewById(R.id.pb_loading);
            tvLoading = itemView.findViewById(R.id.tv_loading);
            llEnd = itemView.findViewById(R.id.ll_end);
        }
    }

    public NewsAdapter(LinkedList<News> myDataset) {
        data = myDataset;
    }

    public NewsAdapter(LinkedList<News> myDataset, String Filter) {
        filter = Filter;
        data = new LinkedList<>();
        if (myDataset != null) {
            for (News i : myDataset) {
                if (i.getType().equals(Filter))
                    data.add(i);
            }
        }

        oData = myDataset;
    }

    public void refresh() {
        if (!filter.equals("")) {
            data = new LinkedList<>();
            for (News i : oData) {
                if (i.getType().equals(filter))
                    data.add(i);
            }
        }
    }

    /*
    public NewsAdapter(LinkedList<NewsItem> myDataset,
                       int filter) {
        if (filter == NewsItem.Cached) {
            showCacheButton = false;
            data = new LinkedList<>();
            for (News i : myDataset) {
                // TODO: 要结合后端改实现。需要有缓存位。
                if (i.getNews() != null && i.getNews().isSaved())
                    data.add(i);
            }
        }

    }
 */
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
            holder1.txt_affl.setText(data.get(position).getAuthor() + "  " + data.get(position).getDate());
            holder1.txt_no.setText(Integer.toString(position + 1));
            holder1.view.item = data.get(position);


            // cache problems
            if (!showCacheButton) {
                holder1.dotButton.setVisibility(View.GONE);
            }
            else {
                holder1.dotButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view1 = inflater.inflate(R.layout.dialog_bottom_newslist, null);
                        dialog.setContentView(view1);
                        dialog.show();
                        Button button = dialog.findViewById(R.id.btnCancel);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        Button button1 = dialog.findViewById(R.id.btnCache);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ((NewsItemLayout)(view.getParent().getParent().getParent().getParent())).item.saveContent();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(v.getContext(), "内容已缓存", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
            if (data.get(position).isSaved())
                holder1.txt_main.setTextColor(Color.parseColor("#80000000"));
            else
                holder1.txt_main.setTextColor(Color.parseColor("#ff000000"));
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