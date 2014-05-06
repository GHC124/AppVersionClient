package com.ghc.appversionclient.content;

import android.app.Fragment;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghc.appversionclient.content.apps.AppsFragment;

public class ContentDetailsViewFactory {
	/**
	 * Menu items that is show in main activity
	 * 
	 * @author ChungPV1
	 * 
	 */
	public enum MenuItem {
		APPS(0, AppsFragment.class);

		private int value;
		private Class<? extends IContentDetailView> viewClass;

		private static final SparseArray<MenuItem> ENUM_MAP = new SparseArray<MenuItem>();
		static {
			for (MenuItem e : MenuItem.values()) {
				ENUM_MAP.put(e.value, e);
			}
		}

		private MenuItem(int value,
				Class<? extends IContentDetailView> viewClass) {
			this.value = value;
			this.viewClass = viewClass;
		}

		public IContentDetailView createView() {
			if (viewClass != null) {
				try {
					return viewClass.newInstance();
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			return null;
		}

		public static MenuItem valueOf(int value) {
			return ENUM_MAP.get(value);
		}
	}

	public static View createView(Context context, LayoutInflater inflater,
			ViewGroup container, MenuItem item, Fragment fragment) {
		IContentDetailView detailsView = item.createView();
		return detailsView.getView(context, inflater, container, fragment);
	}
}
