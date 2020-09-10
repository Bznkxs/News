package com.example.duyufeng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/25 0025.
 */
public class EntityExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groups;
    private ArrayList<ArrayList<NodeShowPair>> items;
    private Context mContext;

    public EntityExpandableListAdapter(ArrayList<String> groups, ArrayList<ArrayList<NodeShowPair>> items, Context mContext) {


        this.groups = groups;
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(groupPosition).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {

        return items.get(groupPosition).get(childPosition).strTxt;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.entity_explist_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.groupText = (TextView) convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.groupText.setText(groups.get(groupPosition));
        return convertView;
    }



    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.entity_explist_item, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.itemText = convertView.findViewById(R.id.tv_name);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        itemHolder.itemText.setText(items.get(groupPosition).get(childPosition).strTxt);
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ViewHolderGroup{
        private TextView groupText;
    }

    private static class ViewHolderItem{
        private TextView itemText;
    }

}