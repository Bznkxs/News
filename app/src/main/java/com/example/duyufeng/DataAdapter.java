package com.example.duyufeng;


import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private LinkedList<PandemicData> data;
    public static int TYPE_HEADER = 0, TYPE_NORMAL = 1;
    private View header;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName, txtActive, txtOverall, txtDeath, txtCured,
                        txtNewActive, txtNewCases, txtNewDeath, txtNewCured;
        public com.example.duyufeng.DataItemLayout view;
        public MyViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.textRegion);
            txtActive = v.findViewById(R.id.textActiveCases);
            txtNewActive = v.findViewById(R.id.textNewActive);
            txtOverall = v.findViewById(R.id.textOverallCases);
            txtNewCases = v.findViewById(R.id.textNewCases);
            txtDeath = v.findViewById(R.id.textDeaths);
            txtNewDeath = v.findViewById(R.id.textNewDeaths);
            txtCured = v.findViewById(R.id.textCured);
            txtNewCured = v.findViewById(R.id.textNewCured);
            view     = (com.example.duyufeng.DataItemLayout)v;
        }
        public MyViewHolder(View v, int dumb) {
            super(v);
        }
    }

    public static class HeaderHolder extends MyViewHolder {

        public HeaderHolder(View v) {
            super(v, 0);
        }
    }

    public DataAdapter(LinkedList<PandemicData> myDataset) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        position = getPosition(holder);
        PandemicData pos = data.get(position);
        if (pos != null) {
            holder.txtName.setText(pos.getName());
            holder.txtActive.setText(String.valueOf(pos.getActiveCases()));
            holder.txtNewActive.setText(valueOf(pos.getNewActiveCases()));
            holder.txtOverall.setText(String.valueOf(pos.getOverallCases()));
            holder.txtNewCases.setText(valueOf(pos.getNewCases()));
            holder.txtDeath.setText(String.valueOf(pos.getDeaths()));
            holder.txtNewDeath.setText(valueOf(pos.getNewDeaths()));
            holder.txtCured.setText(String.valueOf(pos.getCured()));
            holder.txtNewCured.setText(valueOf(pos.getNewCured()));
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