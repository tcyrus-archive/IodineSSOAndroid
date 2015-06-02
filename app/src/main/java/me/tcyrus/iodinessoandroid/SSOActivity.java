package me.tcyrus.iodinessoandroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class SSOActivity extends ActionBarActivity {

	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sso);
		Intent intent = getIntent();
		String sso = intent.getStringExtra(MainActivity.SSO_MESSAGE);
		try {
			String url = "https://iodine.tjhsst.edu/ajax/sso/valid_key?sso="+sso;
			Log.d("URL", url);
			webView = (WebView) findViewById(R.id.webView);
			webView.loadUrl(url);
		} catch (Exception e) {Log.d("Exception",e.toString());}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sso, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
