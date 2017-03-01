package com.gyz.androidopensamples.rxandroidandretrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.rxandroidandretrofit.networks.NetworkWrapper;
import com.gyz.androidopensamples.rxandroidandretrofit.networks.RepoListAdapter;

import butterknife.ButterKnife;

/**
 * GitHub的库详细页面
 * <p>
 */
public class NetworkDetailActivity extends Activity {

    private static final String USER_KEY = "network_detail_activity.user";

    RecyclerView mRvList;

    public static Intent from(Context context, String username) {
        Intent intent = new Intent(context, NetworkDetailActivity.class);
        intent.putExtra(USER_KEY, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
        mRvList = (RecyclerView) findViewById(R.id.network_rv_list);
        // 设置布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(manager);

        // 设置Adapter
        RepoListAdapter adapter = new RepoListAdapter();
        NetworkWrapper.getReposInfo(getIntent().getStringExtra(USER_KEY), adapter);
        mRvList.setAdapter(adapter);
    }
}
