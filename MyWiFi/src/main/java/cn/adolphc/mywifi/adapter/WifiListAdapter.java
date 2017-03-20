package cn.adolphc.mywifi.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.adolphc.mywifi.R;

/**
 * Created by jaunty on 2016/11/9.
 */

public class WifiListAdapter extends BaseAdapter{
    private List<ScanResult> scanResultList;
    private Context context;
    private LayoutInflater inflater;
    public WifiListAdapter(Context context, List<ScanResult> scanResultList) {
        this.context = context;
        this.scanResultList = scanResultList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return scanResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return scanResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_wifi,null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        String wifiName = scanResultList.get(position).SSID;
        holder.tvName.setText(wifiName);
        holder.ivWifi.setImageResource(R.mipmap.icon_wifi_light);
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.iv_item_wifi_list)
        ImageView ivWifi;
        @ViewInject(R.id.tv_item_wifi_list)
        TextView tvName;
    }
}
