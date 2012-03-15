package dk.itu.info.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PickAccountList extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AccountManager accountManager = AccountManager.get(getApplicationContext());
		Account[] accounts = accountManager.getAccountsByType("com.google");
		
		if(accounts.length == 1) {
			onAccountSelect(accounts[0]);
		} else {
			this.setListAdapter(new ArrayAdapter<Account>(this, R.layout.list_item, accounts));
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Account account = (Account) getListView().getItemAtPosition(position);
		onAccountSelect(account);
	}
	
	private void onAccountSelect(Account account) {
		Intent result = new Intent();
		result.putExtra("account", account);
		setResult(1, result);
		finish();
	}
}
