package com.example.sjb0903.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sjb0903.R;
import com.example.sjb0903.bean.Goods;
import com.example.sjb0903.view.MainActivity;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Goods.GoodsInfo> data;

    public MyAdapter(Context context, List<Goods.GoodsInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item,null);
            viewHolder=new ViewHolder();
            viewHolder.iv=convertView.findViewById(R.id.iv);
            viewHolder.tv1=convertView.findViewById(R.id.tv1);
            viewHolder.tv2=convertView.findViewById(R.id.tv2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Glide.with(context).load(data.get(position).getGoods_thumb()).into(viewHolder.iv);
        viewHolder.tv1.setText(data.get(position).getCurrency_price());
        viewHolder.tv2.setText(data.get(position).getGoods_name());
        return convertView;
    }
    class ViewHolder{
        private ImageView iv;
        private TextView tv1;
        private TextView tv2;
    }
}
