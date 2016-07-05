package com.chans.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * @author
 *
 */
public class PlusActionProvider extends ActionProvider {
	private Context context;

	public PlusActionProvider(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {

		subMenu.clear();
		subMenu.add("haha").setIcon(R.drawable.file_icon).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				context.startActivity(new Intent(context, FindFiles.class));
				return true;
			}
		});
		subMenu.add("hehe").setIcon(R.drawable.file_icon).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return true;
			}
		});
		subMenu.add("kakka").setIcon(R.drawable.file_icon).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return true;
			}
		});
		super.onPrepareSubMenu(subMenu);
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}

}
