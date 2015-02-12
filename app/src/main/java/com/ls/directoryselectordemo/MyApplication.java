package com.ls.directoryselectordemo;

import android.app.Application;

import java.io.File;

public class MyApplication extends Application {

	public final AppSettings settings = new AppSettings(this);

	@Override
	public void onCreate() {
		super.onCreate();

		settings.load();
		if (!settings.isInitialized()) {
			settings.setInitialized(true);
			File file = getExternalFilesDir(null);
			if (file != null) settings.setStorePath(file.getPath());
			settings.save();
		}
	}
}
