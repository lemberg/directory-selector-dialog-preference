package com.ls.directoryselectordemo;

import android.app.Activity;
import android.app.Application;
import android.preference.PreferenceManager;

public class AppSettings extends Settings {

	public static AppSettings getSettings(Activity activity) {
		return getSettings(activity.getApplication());
	}

	public static AppSettings getSettings(Application application) {
		return ((MyApplication) application).settings;
	}

	private final MyApplication application;

	public AppSettings(MyApplication application) {
		this.application = application;
	}


	public void load() {
		load(PreferenceManager.getDefaultSharedPreferences(application));
	}

	public void save() {
		save(PreferenceManager.getDefaultSharedPreferences(application));
	}

	public void saveDeferred() {
		saveDeferred(PreferenceManager.getDefaultSharedPreferences(application));
	}
}
