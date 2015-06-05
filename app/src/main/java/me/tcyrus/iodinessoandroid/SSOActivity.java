package me.tcyrus.iodinessoandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSOActivity extends ActionBarActivity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sso);

		Intent intent = getIntent();
		String sso = intent.getStringExtra(MainActivity.SSO_MESSAGE);

		String url = "https://iodine.tjhsst.edu/ajax/sso/valid_key?sso="+sso;
		Log.d("URL", url);

		listView = (ListView) findViewById(R.id.listView);

		String[] urls = new String[] {url};
		new loadJson().execute(urls);
	}

	public void setListView(List<JSONItem> list) {
		ArrayAdapter<JSONItem> adapter = new JSONAdapter(SSOActivity.this, list);
		listView.setAdapter(adapter);
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

	private class loadJson extends AsyncTask<String, Void, List<JSONItem>> {

		protected List<JSONItem> doInBackground(String... urls) {
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			HttpPost httppost = new HttpPost(urls[0]);

			// Depends on your web service
			httppost.setHeader("Content-type", "application/json");

			InputStream inputStream = null;
			String result = null;
			List<JSONItem> list = new ArrayList<JSONItem>();

			try {
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				inputStream = entity.getContent();
				// json is UTF-8 by default
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();

				String line=null;

				while ((line=reader.readLine())!=null) {sb.append(line+"\n");}

				result=sb.toString();

				JSONObject jObject=new JSONObject(result).getJSONObject("sso");

				Iterator<?> keys=jObject.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();
					list.add(new JSONItem(key,jObject.get(key).toString()));
				}
			} catch (Exception e) {Log.d("Exception",e.toString());}
			finally {
				try {if(inputStream!=null)inputStream.close();}
				catch (Exception e) {Log.d("Exception",e.toString());}
			}
			return list;
		}

		protected void onPostExecute(List<JSONItem> list) {
			Log.d("List",list.toString());
			super.onPostExecute(list);
			setListView(list);
		}
	}

}