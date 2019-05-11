package com.artech.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artech.demo.R;
import com.artech.demo.entity.Custom;
import com.artech.demo.activity.VistLogActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by dev on 2017/3/30.
 */

public class CustomAdapter extends BaseAdapter{
    private List<Custom> customList;
    private Context context;
    private LayoutInflater inflater;
    public CustomAdapter(Context context, List<Custom> customList) {
        this.context = context;
        this.customList = customList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return customList.size();
    }

    @Override
    public Object getItem(int position) {
        return customList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_custom,null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        String name = customList.get(position).getCustomName();
        holder.tvCustomName.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Custom custom = customList.get(position);
                Intent intent =new Intent(context,VistLogActivity.class);
                intent.putExtra("custom", custom);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.tv_item_custom_list)
        TextView tvCustomName;
    }
}
