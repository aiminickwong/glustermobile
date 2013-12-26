package org.gluster.mobile.gdisplays;

import java.util.HashMap;
import java.util.List;

import org.gluster.mobile.activities.R;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListDisplay {
	private ListView lists;
	private Context context;
	private List<HashMap<String, String>> listViewParams;
	private int[] column_ids;
	private String[] column_tags;

	public ListDisplay(ListView lists, Context context, List<HashMap<String, String>> listViewParams, int[] column_ids, String[] column_tags) {
		super();
		this.lists = lists;
		this.context = context;
		this.listViewParams = listViewParams;
		this.column_ids = column_ids;
		this.column_tags = column_tags;
	}

	public ListView getLists() {
		return lists;
	}

	public void setLists(ListView lists) {
		this.lists = lists;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<HashMap<String, String>> getListViewParams() {
		return listViewParams;
	}

	public void setListViewParams(List<HashMap<String, String>> listViewParams) {
		this.listViewParams = listViewParams;
	}

	public int[] getColumn_ids() {
		return column_ids;
	}

	public void setColumn_ids(int[] column_ids) {
		this.column_ids = column_ids;
	}

	public String[] getColumn_tags() {
		return column_tags;
	}

	public void setColumn_tags(String[] column_tags) {
		this.column_tags = column_tags;
	}

	public void display() {
		if (listViewParams.size() == 0) {
			String nothingToDisplay[] = new String[1];
			nothingToDisplay[0] = "Nothing To Display";
			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(context, R.layout.list_view_style, nothingToDisplay);
			lists.setAdapter(stringAdapter);
		} else {
			SimpleAdapter adapter = new SimpleAdapter(context, listViewParams, R.layout.list_view_row, column_tags, column_ids);
			lists.setAdapter(adapter);
		}
	}
}
