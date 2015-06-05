package me.tcyrus.iodinessoandroid;

/**
 * Created by 2016tcyrus on 6/3/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class JSONAdapter extends ArrayAdapter<JSONItem> {
	public JSONAdapter(Context context, List<JSONItem> list) {
		super(context, 0, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JSONItem item = getItem(position);
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.json_adapter, parent, false);
		}
		TextView location = (TextView) convertView.findViewById(R.id.text);
		location.setText(item.key+" - "+item.value);
		return convertView;
	}
}
class JSONItem {
	String key;
	String value;
	public JSONItem(String key, String value) {
		this.key = key;
		this.value = value;
	}
}