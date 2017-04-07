package com.beijingtest.bjt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.entity.VistLog;
import com.beijingtest.bjt.activity.BaiduMapActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by dev on 2017/3/30.
 */

public class VistLogAdapter extends BaseAdapter{
    private List<VistLog> vistLogList;
    private Context context;
    private LayoutInflater inflater;
    public VistLogAdapter(Context context, List<VistLog> vistLogList) {
        this.context = context;
        this.vistLogList = vistLogList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return vistLogList.size();
    }

    @Override
    public Object getItem(int position) {
        return vistLogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_vist_log,null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        String company = vistLogList.get(position).getCustomName();
        String content = vistLogList.get(position).getVistContent();
        String address = vistLogList.get(position).getAddress();
        holder.tvCompany.setText(company);
        holder.tvContent.setText(content);
        holder.tvAdress.setText("地址：" + address);
        holder.ivToLocation.setOnClickListener(new ToLocation(position));
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Custom custom = customList.get(position);
//                Intent intent =new Intent(context,VistLogActivity.class);
//                intent.putExtra("custom", custom);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.tv_item_vist_log_company)
        TextView tvCompany;
        @ViewInject(R.id.tv_item_vist_log_content)
        TextView tvContent;
        @ViewInject(R.id.tv_item_vist_log_address)
        TextView tvAdress;
        @ViewInject(R.id.iv_item_vist_log_to_location)
        ImageView ivToLocation;
    }

    /**
     * 设置定位监听
     */
    class ToLocation implements View.OnClickListener {
        private int position;
        public ToLocation(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            VistLog vistLog = vistLogList.get(position);
            Intent intent = new Intent(context, BaiduMapActivity.class);
            intent.putExtra("vistLog", vistLog);
            context.startActivity(intent);
        }
    }
}
