package com.oloan.get.app.icon;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class GetAppIconActivity extends ListActivity {
	/** Called when the activity is first created. */
	List<ApplicationInfo> list;
	IconAdapter adapt;
	String TAG = "oloan.icon";
	Drawable dwr;
	BitmapDrawable bDrw;
	File dest;
	int x = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		// list = getPackageManager().getInstalledApplications(
		// PackageManager.GET_META_DATA);
		PackageManager pm = getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		list = pm.getInstalledApplications(0);
		

		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ApplicationInfo a1 = (ApplicationInfo) arg0;
				ApplicationInfo a2 = (ApplicationInfo) arg1;
				return a1
						.loadLabel(getPackageManager())
						.toString()
						.compareToIgnoreCase(
								a2.loadLabel(getPackageManager()).toString());
			}
		});

		File sd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/icons/");
		if (!sd.exists()) {
			sd.mkdir();
		}

		for (ApplicationInfo ai : list) {
			dwr = ai.loadIcon(getPackageManager());
			bDrw = (BitmapDrawable) dwr;
			Bitmap bmp = bDrw.getBitmap();

			if (ai.loadLabel(getPackageManager()).toString()
					.equalsIgnoreCase("SIM Toolkit")) {
				dest = new File(Environment.getExternalStorageDirectory()
						.getPath()
						+ "/icons/"
						+ ai.loadLabel(getPackageManager()) + x + ".png");
				x++;
			} else {
				dest = new File((Environment.getExternalStorageDirectory()
						.getPath()
						+ "/icons/"
						+ ai.loadLabel(getPackageManager()) + ".png"));
			}

			try {
				Log.v(TAG, "writing");
				FileOutputStream out = new FileOutputStream(dest);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				Log.v(TAG, "ex : " + e.getMessage());
			}
		}

		adapt = new IconAdapter(this, list, getPackageManager());
		Log.v(TAG, "isi total = " + adapt.getCount());
		setListAdapter(adapt);

	}
}