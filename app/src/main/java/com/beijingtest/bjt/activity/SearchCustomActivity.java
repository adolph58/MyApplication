package com.beijingtest.bjt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.beijingtest.bjt.R;
import com.beijingtest.bjt.adapter.CustomAdapter;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.util.SearchUtils;
import com.beijingtest.bjt.util.Tools;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class SearchCustomActivity extends BaseActivity {
    @ViewInject(R.id.iv_search_custom_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_search_custom)
    private EditText etSearch;
    @ViewInject(R.id.iv_search_custom)
    private ImageView ivSearch;
    @ViewInject(R.id.lv_search_custom)
    private ListView listView;

    private List<Custom> originalCustomList;
    @Override
    protected void initVariables() {
        //Intent intent = getIntent();
        originalCustomList = Custom.getCustomListByFile();  //(List<Custom>)intent.getSerializableExtra("customList");
        System.out.println(originalCustomList.toString());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_custom);
        x.view().inject(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void search() {
        String searchContent = etSearch.getText().toString().trim();
        List<Custom> searchCustomList = SearchUtils.searchCustom(originalCustomList, searchContent);
        if (searchCustomList.size() > 0 ) {
            CustomAdapter adapter = new CustomAdapter(this,searchCustomList);
            listView.setAdapter(adapter);
        } else {
            Tools.showToast("没有搜到客户");
        }
    }
}
