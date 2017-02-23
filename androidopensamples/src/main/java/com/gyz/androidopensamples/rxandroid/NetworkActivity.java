package com.gyz.androidopensamples.rxandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.rxandroid.networks.NetworkWrapper;
import com.gyz.androidopensamples.rxandroid.networks.UserListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Rx的网络请求方式
 * <p>
 */
public class NetworkActivity extends Activity {

    RecyclerView mRvList; // 列表

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
        mRvList = (RecyclerView) findViewById(R.id.network_rv_list);
        // 设置Layout管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);

        // 设置适配器
        UserListAdapter adapter = new UserListAdapter(this::gotoDetailPage);
        NetworkWrapper.getUsersInto(adapter);
        mRvList.setAdapter(adapter);
    }

    // 点击的回调
    public interface UserClickCallback {
        void onItemClicked(String name);
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String name) {
        startActivity(NetworkDetailActivity.from(NetworkActivity.this, name));
    }
}
