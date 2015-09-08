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
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * ����ƴ��������ListView�����������
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

		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
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
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
			Toast.makeText(MainActivity.this,mainAdapter.getItem(position).getClass().getName(), Toast.LENGTH_SHORT);
			}
		});

		// ����a-z��������Դ����
		Collections.sort(SourceDateList, pinyinComparator);
		mainAdapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(mainAdapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�������Ϊ���������б�
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
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(ArrayList<FriendBean> data) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < data.size(); i++) {
			SortModel sortModel = new SortModel();
			sortModel.setFriend(data.get(i));
			// ����ת����ƴ��
			// String pinyin = characterParser.getSelling(data.get(i)
			// .getName());
			String sortString = data.get(i).getName().substring(0, 1)
					.toUpperCase();
			// �������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
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
	 * ����������е�ֵ���������ݲ�����ListView
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

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		mainAdapter.updateListView(filterDateList);
	}
}