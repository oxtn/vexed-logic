package com.austinrasmussen.stockviewer;

import java.util.List;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class StockList extends ListActivity {

	private List<StockQuote> quoteResult;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		StockQuoteFetcher sqf = new StockQuoteFetcher("MSFT ORCL AMZN ERTS");
		quoteResult = sqf.Fetch();

		setListAdapter(new StockQuoteAdapter(this, quoteResult));
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.stock_item_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.remove_item:
			quoteResult.remove(info.position);
			((StockQuoteAdapter)getListAdapter()).notifyDataSetChanged();
			return true;
		}
		return false;
	}
}
