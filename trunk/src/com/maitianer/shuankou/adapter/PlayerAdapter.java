package com.maitianer.shuankou.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.model.PlayerModel;

public class PlayerAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private ArrayList<PlayerModel> mModels;
	private int type;

	public PlayerAdapter (Context context, ArrayList<PlayerModel> dataModels, int type) {
		mModels = dataModels;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}

	public int getCount () {
		return mModels.size();
	}

	public PlayerModel getItem (int position) {
		return mModels.get(position);
	}

	public long getItemId (int position) {
		return position;
	}

	@Override
	public int getItemViewType (int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount () {
		return 1;
	}

	@SuppressLint("InflateParams")
	public View getView (int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			switch (type) {
				case 1:
					convertView = inflater.inflate(R.layout.item_menu, null);
					break;
				case 2:
					convertView = inflater.inflate(R.layout.item_menu, null);
					break;
				case 3:
					convertView = inflater.inflate(R.layout.item_playerlist, null);
					break;

				default:
					break;
			}
			holder = new ViewHolder();
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PlayerModel data = getItem(position);
		holder.fillView(data);
		return convertView;
	}

	class ViewHolder
	{
		TextView lbl_title;

		public void loadViews (View convertView) {
			lbl_title = (TextView) convertView.findViewById(R.id.lbl_title);
		}

		public void fillView (PlayerModel data) {
			lbl_title.setText(data.getName());
		}
	}
}