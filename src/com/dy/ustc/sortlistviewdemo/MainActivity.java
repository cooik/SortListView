package com.dy.ustc.sortlistviewdemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dy.ustc.sortlistviewdemo.SideBar.OnTouchingLetterChangedListener;
import com.dy.ustc.sortlistviewdemo.bean.FriendBean;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter mainAdapter;
	private ClearEditText mClearEditText;
	private ArrayList<FriendBean> childData;
	// private ArrayList<String> childname;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initData();
		initViews();
	}

	private void initData() {
		childData = new ArrayList<FriendBean>();
		for (int i = 0; i < 5; i++)

		{
			FriendBean item = new FriendBean();
			item.setName("rimo");
			item.setDescription("dfsasdsssdfsdfsdfsf");
			childData.add(item);
			// childname.add(item.getName());
		}
		for (int i = 0; i < 5; i++)

		{
			FriendBean item = new FriendBean();
			item.setName("dsimo");
			item.setDescription("dfsasdsssdfsdfsdfsf");
			childData.add(item);
			// childname.add(item.getName());

		}
		for (int i = 0; i < 5; i++)

		{
			FriendBean item = new FriendBean();
			item.setName("gsimo");
			item.setDescription("dfsasdsssdfsdfsdfsf");
			childData.add(item);

		}
		SourceDateList = filledData(childData);
	}

	private void initViews() {

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = mainAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
			Toast.makeText(MainActivity.this,mainAdapter.getItem(position).getClass().getName(), Toast.LENGTH_SHORT);
			}
		});

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		mainAdapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(mainAdapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(ArrayList<FriendBean> data) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < data.size(); i++) {
			SortModel sortModel = new SortModel();
			sortModel.setFriend(data.get(i));
			// 汉字转换成拼音
			// String pinyin = characterParser.getSelling(data.get(i)
			// .getName());
			String sortString = data.get(i).getName().substring(0, 1)
					.toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getFriend().getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		mainAdapter.updateListView(filterDateList);
	}
}
