package com.example.duyufeng;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class NewsAdapter extends RecyclerView.Adapter<com.example.duyufeng.NewsAdapter.MyViewHolder> {
    private LinkedList<NewsItem> data;

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

    public NewsAdapter(LinkedList<NewsItem> myDataset) {
        data = myDataset;
    }

    public NewsAdapter() {
        data = null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View convertView = new com.example.duyufeng.NewsItemLayout(parent.getContext());
        //View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_without_icon,parent,false);
        MyViewHolder holder = new MyViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt_main.setText(data.get(position).getName());
        holder.txt_affl.setText(data.get(position).getAuthor());
        holder.txt_no.setText(Integer.toString(position + 1));
        holder.view.item = data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}