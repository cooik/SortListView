package com.dy.ustc.sortlistviewdemo.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.dy.ustc.sortlistviewdemo.R;
import com.dy.ustc.sortlistviewdemo.bean.FriendBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<FriendBean> mFriends;
	private LayoutInflater inflater;

	public FriendsAdapter(Context context, ArrayList<FriendBean> friends) {
		mContext = context;
		mFriends = friends;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return mFriends.size();
	}

	@Override
	public Object getItem(int position) {
		return mFriends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FriendBean friend = mFriends.get(position);
		ViewHolder holder;
		View view;
		if (convertView == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_item_layout, parent, false);
			holder.head = (ImageView) view.findViewById(R.id.list_item_head);
			holder.name = (TextView) view.findViewById(R.id.list_item_text1);
			holder.des = (TextView) view.findViewById(R.id.list_item_text2);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		holder.head.setBackgroundResource(R.drawable.login_pho);
		holder.name.setText(friend.getName());
		holder.des.setText(friend.getDescription());
		return view;
	}
 

	class ViewHolder {
		ImageView head;
		TextView name;
		TextView des;

	}

 
}
