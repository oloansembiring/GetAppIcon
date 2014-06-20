package com.oloan.get.app.icon;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconAdapter extends BaseAdapter{

	Context mContext;
	List<ApplicationInfo> mList;
	PackageManager mPackageManager;
	//OloanYahoo added this line 20140620

	public IconAdapter(Context mContext, List<ApplicationInfo> mList,
			PackageManager mPackageManager) {	
		this.mContext = mContext;
		this.mList = mList;
		this.mPackageManager = mPackageManager;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ApplicationInfo entry = (ApplicationInfo) mList.get(position);

		// reference to convertView
		View v = convertView; 

		// inflate new layout if null
		if(v == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			v = inflater.inflate(R.layout.list_adapter, null);
		} 

		ImageView ivAppIcon = (ImageView)v.findViewById(R.id.image);
		TextView tv = (TextView) v.findViewById(R.id.text);
		
		ivAppIcon.setImageDrawable(entry.loadIcon(mPackageManager));		
		tv.setText(entry.loadLabel(mPackageManager));
		
		return v;

	}

}
