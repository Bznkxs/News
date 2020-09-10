package com.example.duyufeng;


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<StatInfo> data;
    public static int TYPE_HEADER = 0, TYPE_NORMAL = 1;
    private View header;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName, txtConfirmed, txtSuspected, txtDeath, txtCured;
        public com.example.duyufeng.DataItemLayout view;
        public MyViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.textRegion);
            txtConfirmed = v.findViewById(R.id.textConfirmed);
            //txtSuspected = v.findViewById(R.id.textSuspected);
            txtDeath = v.findViewById(R.id.textDeaths);
            txtCured = v.findViewById(R.id.textCured);
            view     = (com.example.duyufeng.DataItemLayout)v;
        }
        public MyViewHolder(View v, int dumb) {
            super(v);
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View v) {
            super(v);
        }
    }

    public DataAdapter(ArrayList<StatInfo> myDataset) {
        data = myDataset;
    }

    public DataAdapter() {
        data = null;
    }

    public void setHeaderView(View headerView) {
        this.header = headerView;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if(header != null && viewType == TYPE_HEADER)
            return new HeaderHolder(header);
        // create a new view
        View convertView = new com.example.duyufeng.DataItemLayout(parent.getContext());
        //View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_without_icon,parent,false);
        MyViewHolder holder = new MyViewHolder(convertView);
        return holder;
    }

    public int getPosition(MyViewHolder holder) {
        int pos = holder.getLayoutPosition();
        return header == null ? pos : pos - 1;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        MyViewHolder mholder = (MyViewHolder) holder;
        position = getPosition(mholder);
        StatInfo pos = data.get(position);
        if (pos != null) {
            mholder.txtName.setText(pos.getName());
            mholder.txtConfirmed.setText(pos.getCONFIRMED());
            //mholder.txtSuspected.setText(pos.getSUSPECTED());
            mholder.txtDeath.setText(pos.getDEAD());
            mholder.txtCured.setText(pos.getCURED());
        }

    }

    String valueOf(long num) {
        String str = String.valueOf(num);
        if (num >= 0) str = "+" + str;
        return str;
    }

    @Override
    public int getItemViewType(int position) {
        if (header == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {
        if (header == null)
            return data.size();
        return data.size() + 1;
    }
}