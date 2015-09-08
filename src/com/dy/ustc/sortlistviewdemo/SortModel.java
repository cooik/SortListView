package com.dy.ustc.sortlistviewdemo;

import java.util.ArrayList;

import com.dy.ustc.sortlistviewdemo.adapter.FriendsAdapter;
import com.dy.ustc.sortlistviewdemo.bean.FriendBean;

public class SortModel {

	// private String name;//显示的数据
	private String sortLetters;// 显示数据拼音的首字母
	FriendBean friend;
	public FriendBean getFriend() {
		return friend;
	}

	public void setFriend(FriendBean friend) {
		this.friend = friend;
	}


	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	

}
