package com.gyz.androidopensamples.rxandroid.networks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.rxandroid.NetworkActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 显示列表
 * <p>
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<GitHubUser> mUsers; // 用户名集合

    private NetworkActivity.UserClickCallback mCallback; // 用户点击项的回调

    public UserListAdapter(NetworkActivity.UserClickCallback callback) {
        mUsers = new ArrayList<>();
        mCallback = callback;
    }

    public void addUser(GitHubUser user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1); // 最后一位
    }

    @Override public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_network_user, parent, false);
        return new UserViewHolder(item, mCallback);
    }

    @Override public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bindTo(mUsers.get(position));
    }

    @Override public int getItemCount() {
        return mUsers.size();
    }

    // Adapter的ViewHolder
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvUserPicture;
        TextView mTvUserName;
        TextView mTvUserLogin;
        TextView mTvUserPage;

        public UserViewHolder(View itemView, NetworkActivity.UserClickCallback callback) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mIvUserPicture = (ImageView) itemView.findViewById(R.id.network_item_iv_user_picture);
            mTvUserName = (TextView) itemView.findViewById(R.id.network_item_tv_user_name);
            mTvUserLogin = (TextView) itemView.findViewById(R.id.network_item_tv_user_login);
            mTvUserPage = (TextView) itemView.findViewById(R.id.network_item_tv_user_page);
            // 绑定点击事件
            itemView.setOnClickListener(v ->
                    callback.onItemClicked(mTvUserLogin.getText().toString()));
        }

        // 绑定数据
        public void bindTo(GitHubUser user) {


            mTvUserName.setText(user.name);
            mTvUserLogin.setText(user.login);
            mTvUserPage.setText(user.repos_url);

            Picasso.with(mIvUserPicture.getContext())
                    .load(user.avatar_url)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(mIvUserPicture);
        }
    }

    // 用户类, 名称必须与Json解析相同
    public static class GitHubUser {
        public String login;
        public String avatar_url;
        public String name;
        public String repos_url;
    }
}
