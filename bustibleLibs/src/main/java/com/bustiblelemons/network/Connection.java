package com.bustiblelemons.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.bustiblelemons.settings.BaseSettings;

/**
 * Created 5 Nov 2013
 */
public class Connection {
	private static ConnectivityManager CONNECTIVITY_MANAGER;
	private static NetworkInfo INFO;

	private synchronized static ConnectivityManager getConnectivityManager(Context context) {
		return Connection.CONNECTIVITY_MANAGER == null ? Connection.CONNECTIVITY_MANAGER = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE) : Connection.CONNECTIVITY_MANAGER;
	}

	public synchronized static boolean isWiFiUp(Context context) {
		return getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public synchronized static boolean isWiMaxUp(Context context) {
		Connection.INFO = getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		return Connection.INFO != null ? Connection.INFO.isConnected() : false;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private synchronized static boolean isEthernetUp(Context context) {
		Connection.INFO = getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
		return Connection.INFO != null ? Connection.INFO.isConnected() : false;
	}

	public synchronized static boolean isMobileUp(Context context) {
		Connection.INFO = getConnectivityManager(context).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return Connection.INFO != null ? Connection.INFO.isConnected() : false;
	}

	public synchronized static boolean isOhterUp(Context context) {
		return Connection.isWiFiUp(context) || Connection.isWiMaxUp(context) || Connection.isEthernetUp(context);
	}

	public synchronized static boolean isUp(Context context) {
		return BaseSettings.useMobileData(context) ? Connection.isMobileUp(context) || Connection.isOhterUp(context)
				: Connection.isOhterUp(context);
	}
}
