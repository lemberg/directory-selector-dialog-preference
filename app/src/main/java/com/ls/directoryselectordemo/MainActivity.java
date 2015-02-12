package com.ls.directoryselectordemo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ls.directoryselector.DirectoryDialog;

import java.io.File;


public class MainActivity extends Activity implements DirectoryDialog.Listener {

	private AppSettings settings;

	private TextView txtDirLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		settings = AppSettings.getSettings(this);

		initViews();
		fillViews();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			SettingsActivity.startThisActivity(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
	}

	@Override
	public void onResume() {
		super.onResume();
		settings.load();
		fillViews();
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
	}

	//region DirectoryDialog.Listener interface
	@Override
	public void onDirectorySelected(File dir) {
		settings.setStorePath(dir.getPath());
		settings.saveDeferred();
		fillViews();
	}

	@Override
	public void onCancelled() {
	}
	//endregion

	private final SharedPreferences.OnSharedPreferenceChangeListener sharedPrefsChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			settings.load();
			fillViews();
		}
	};

	private final View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_change_dir) {
				DialogFragment dialog = DirectoryDialog.newInstance(settings.getStorePath());
				dialog.show(getFragmentManager(), "directoryDialog");
			}
		}
	};

	private void initViews() {
		txtDirLocation = (TextView) findViewById(R.id.txt_dir_location);
		Button btnChangeDir = (Button) findViewById(R.id.btn_change_dir);
		btnChangeDir.setOnClickListener(clickListener);
	}

	private void fillViews() {
		txtDirLocation.setText(settings.getStorePath());
	}
}
