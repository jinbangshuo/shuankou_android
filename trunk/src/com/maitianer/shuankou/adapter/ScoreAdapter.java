package com.maitianer.shuankou.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maitianer.shuankou.R;
import com.maitianer.shuankou.model.ScoreModel;
import com.maitianer.shuankou.util.MyApplication;

public class ScoreAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private ArrayList<ScoreModel> mModels;
	private int type;

	public ScoreAdapter (Context context, ArrayList<ScoreModel> dataModels, int type) {
		mModels = dataModels;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}

	public int getCount () {
		return mModels.size();
	}

	public ScoreModel getItem (int position) {
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
					convertView = inflater.inflate(R.layout.item_result_1, null);
					break;
				case 2:
					convertView = inflater.inflate(R.layout.item_result_2, null);
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
		ScoreModel data = getItem(position);
		holder.fillView(data);
		return convertView;
	}

	class ViewHolder
	{
		TextView lbl_title;

		TextView lbl_name, lbl_score;
		View view_score;

		public void loadViews (View convertView) {
			switch (type) {
				case 1:
					lbl_title = (TextView) convertView.findViewById(R.id.lbl_title);
					break;
				case 2:
					lbl_name = (TextView) convertView.findViewById(R.id.lbl_name);
					view_score = convertView.findViewById(R.id.view_score);
					lbl_score = (TextView) convertView.findViewById(R.id.lbl_score);
					break;

				default:
					break;
			}
		}

		public void fillView (ScoreModel data) {
			switch (type) {
				case 1:
					lbl_title.setText(data.getTitle());
					break;

				case 2:
					lbl_name.setText(data.getTitle());
					lbl_score.setText(MyApplication.FormatNumber(data.getaScore()));
					int width = 0;
					float count = Math.abs(data.getaScore());
					float allcount = data.getAllScore();
					float maxlen = data.getMaxLen();
					if (allcount == 0.0) {
						width = 20;
					} else {
						width =(int) ((count * 1.0 / allcount) * maxlen * (2.0 / 3.0));
					}
					if (width == 0 || width < 20) {
						width = 20;
					}
					if (width>maxlen) {
						width=(int)maxlen;
					}
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, 20);
					view_score.setLayoutParams(lp);
					if (data.getaScore() > 0) {
						view_score.setBackgroundResource(R.drawable.bg_style_view_yellow);
					} else {
						view_score.setBackgroundResource(R.drawable.bg_style_view_green);
					}
					break;
				default:
					break;
			}

		}
	}
}
